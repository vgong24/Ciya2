package com.victoweng.ciya2.repository

import android.location.Location
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.victoweng.ciya2.comparator.TimeStampComparator
import com.victoweng.ciya2.constants.*
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.data.chat.ChatRoom
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener

object FireStoreRepo {

    private val TAG = FireStoreRepo::class.java.canonicalName

    val fireStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val geoFireStore: GeoFirestore by lazy {
        GeoFirestore(fireStore.collection(EVENT_GEO_FIRE))
    }

    fun createUser(userProfile: UserProfile): Task<Void>? {
        val ref = fireStore.collection("users")
            .document(FireAuth.getCurrentUserId()!!)
            .set(userProfile)
        return ref
    }

    fun getCurrentUserRef(): DocumentReference {
        return fireStore.collection("users").document(FireAuth.getCurrentUserId()!!)
    }

    fun addParticipant(eventDetail: EventDetail, profile: UserProfile, onSuccess: () -> Unit) {
        Log.d(TAG, "add participant to ${eventDetail.eventId}")
        var eventDetailsRef = fireStore.collection(FIRE_EVENT_DETAILS).document(eventDetail.eventId)
        var eventAttendingRef = getCurrentUserRef().collection(FIRE_EVENTS_ATTENDING).document(eventDetail.eventId)
        fireStore.runBatch { writeBatch ->
            writeBatch.update(eventDetailsRef, FIRE_PARTICIPANT_USERS, FieldValue.arrayUnion(profile))
            writeBatch.set(eventAttendingRef, ChatRoom(roomId = eventDetail.eventId, title = eventDetail.title))
            onSuccess()
        }
    }

    fun removeParticipant(eventId: String, profile: UserProfile, onComplete: () -> Unit) {

        var eventDetailsRef = fireStore.collection(FIRE_EVENT_DETAILS).document(eventId)
        var eventAttendingRef = getCurrentUserRef().collection(FIRE_EVENTS_ATTENDING).document(eventId)
        fireStore.runBatch { writeBatch ->
            writeBatch.update(eventDetailsRef, FIRE_PARTICIPANT_USERS, FieldValue.arrayRemove(profile))
            writeBatch.delete(eventAttendingRef)
            onComplete()
        }
    }

    //https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android
    fun fetchLocalEvents(location: Location, listCallback: (MutableList<EventDetail>) -> Unit) {

        val eventIdlist = mutableListOf<String>()
        geoFireStore.queryAtLocation(GeoPoint(location.latitude, location.longitude), 8000.0)
            .addGeoQueryEventListener(object : GeoQueryEventListener {
                override fun onGeoQueryError(exception: Exception) {

                }

                override fun onGeoQueryReady() {
                    Log.d(TAG, "everythings loaded")
                    fetchEventDetailsWithIds(eventIdlist, listCallback)
                }

                override fun onKeyEntered(documentID: String, location: GeoPoint) {
                    Log.d(TAG, "onKeyEntered $documentID")
                    eventIdlist.add(documentID)
                }

                override fun onKeyExited(documentID: String) {

                }

                override fun onKeyMoved(documentID: String, location: GeoPoint) {

                }

            })
    }

    private fun fetchEventDetailsWithIds(
        eventIdlist: MutableList<String>,
        listCallback: (MutableList<EventDetail>) -> Unit
    ) {
        if (eventIdlist.isEmpty()) {
            return
        }
        fireStore.collection("eventDetails")
            .whereIn(FieldPath.documentId(), eventIdlist)
            .get()
            .addOnFailureListener { exception -> Log.d("CLOWN", "failure occurred: ${exception.message}") }
            .addOnSuccessListener {
                if (it.isEmpty) {
                    Log.d(TAG, "event details is empty")
                    listCallback(mutableListOf())
                } else {
                    Log.d(TAG, "loading event details")
                    var eventList = it.toObjects(EventDetail::class.java)
                    eventList = eventList.sortedWith(TimeStampComparator())
                    listCallback(eventList)
                }
            }
    }
}