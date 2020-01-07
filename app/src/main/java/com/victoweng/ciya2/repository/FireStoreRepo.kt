package com.victoweng.ciya2.repository

import android.location.Location
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.victoweng.ciya2.comparator.TimeStampComparator
import com.victoweng.ciya2.constants.*
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.UserProfile
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener

object FireStoreRepo {

    private val TAG = FireStoreRepo::class.java.canonicalName

    val fireStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val geoFireStore: GeoFirestore by lazy {
        GeoFirestore(fireStore.collection(EVENT_GEO_FIRE))
    }

    fun createUser(userProfile: UserProfile) : Task<Void>? {
        val ref = fireStore.collection("users")
            .document(FireAuth.getCurrentUserId()!!)
            .set(userProfile)
        return ref
    }

    fun addParticipant(eventId: String, profile: UserProfile) : Task<Void> {
        Log.d(TAG, "add participant to $eventId")
        return fireStore.collection(FIRE_EVENT_DETAILS).document(eventId)
            .update(FIRE_PARTICIPANT_USERS, FieldValue.arrayUnion(profile))
    }

    fun removeParticipant(eventId: String, profile: UserProfile): Task<Void> {
        return fireStore.collection(FIRE_EVENT_DETAILS).document(eventId)
            .update(FIRE_PARTICIPANT_USERS, FieldValue.arrayRemove(profile))
    }

    //https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android
    fun fetchLocalEvents(location: Location, listCallback: (MutableList<EventDetail>) -> Unit) {

        val eventIdlist = mutableListOf<String>()
        geoFireStore.queryAtLocation(GeoPoint(location.latitude, location.longitude), 8000.0)
            .addGeoQueryEventListener(object: GeoQueryEventListener{
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