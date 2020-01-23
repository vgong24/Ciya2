package com.victoweng.ciya2.repository.event

import android.location.Location
import android.util.Log
import com.google.firebase.firestore.*
import com.victoweng.ciya2.comparator.TimeStampComparator
import com.victoweng.ciya2.constants.EVENT_GEO_FIRE
import com.victoweng.ciya2.constants.FIRE_EVENT_DETAILS
import com.victoweng.ciya2.constants.FIRE_HOST
import com.victoweng.ciya2.constants.FIRE_PARTICIPANT_USERS
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.ChatMessagesAPI
import com.victoweng.ciya2.repository.auth.AuthRepo
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.extension.setLocation
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener
import javax.inject.Inject

class EventApi @Inject constructor(
    val firestore: FirebaseFirestore,
    val chatMessagesAPI: ChatMessagesAPI,
    val authRepo: AuthRepo
) {

    private val TAG = EventApi::class.java.canonicalName

    //References and Documents
    fun createGeoFire() = GeoFirestore(firestore.collection(EVENT_GEO_FIRE))

    fun getEventDetailsCollection() = firestore.collection(FIRE_EVENT_DETAILS)

    fun getEventDoc(eventId: String) = getEventDetailsCollection().document(eventId)

    fun createEventDetailReference() = getEventDetailsCollection().document()

    //Functions
    fun createEvent(eventDetail: EventDetail, eventDetailRef: DocumentReference, onComplete: (EventDetail) -> Unit) {
        val hostRef = authRepo.getCurrentUserDocument()
        val geoRef = createGeoFire()
        firestore.runBatch { writeBatch ->
            writeBatch.set(eventDetailRef, eventDetail)
            writeBatch.update(eventDetailRef, FIRE_HOST, hostRef)
            chatMessagesAPI.createChatRoom(eventDetail, writeBatch)
            //Chatroom should be added into eventDetail
            Log.d(TAG, "DoesEventHaveChatroom: ${eventDetail.chatRoom}");
            writeBatch.set(eventDetailRef, eventDetail)

        }.addOnCompleteListener {
            if (it.isSuccessful) {
                geoRef.setLocation(
                    eventDetail.eventId,
                    GeoPoint(eventDetail.eventLocation.lat, eventDetail.eventLocation.lon)
                ) { exception ->
                    exception?.let { Log.d(TAG, "geoPoint location saved successfully to Firestore: $geoRef") }
                }
                onComplete(eventDetail)
            } else {
                Log.e(TAG, "Error attempting to create Event: ${it.exception?.message}");
            }
        }
    }

    /**
     * https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android
     * Grabs nearby events via GeoFire query and returns the collection of events based on the geoIds
     */
    fun fetchLocalEvents(location: Location, listCallback: (MutableList<EventDetail>) -> Unit) {
        val eventIdlist = mutableListOf<String>()
        createGeoFire().queryAtLocation(GeoPoint(location.latitude, location.longitude), 8000.0)
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
        eventIdList: MutableList<String>,
        listCallback: (MutableList<EventDetail>) -> Unit
    ) {
        if (eventIdList.isEmpty()) {
            Log.d(TAG, "fetchEventDetailsWithIds: id list is Empty. Return empty events");
            listCallback(mutableListOf())
            return
        }

        getEventDetailsCollection().whereIn(FieldPath.documentId(), eventIdList)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.let {
                        var resultList = it.toObjects(EventDetail::class.java)
                        resultList = resultList.sortedWith(TimeStampComparator())
                        listCallback(resultList)
                        Log.d(TAG, "fetchEventDetails with size: ${resultList.size}")
                    } ?: listCallback(mutableListOf())
                }
            }
    }

    fun addParticipant(eventDetail: EventDetail, profile: UserProfile, onComplete: (EventDetail) -> Unit) {
        val eventDetailRef = getEventDoc(eventDetail.eventId)
        val eventAttendingRef = authRepo.getCurrentlyAttendingEvent(eventDetail.eventId)
        firestore.runBatch { writeBatch ->
            writeBatch.update(eventDetailRef, FIRE_PARTICIPANT_USERS, FieldValue.arrayUnion(profile))
            writeBatch.set(eventAttendingRef, eventDetail.chatRoom)
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "addParticipant: successful");
                onComplete(eventDetail)
            } else {
                Log.e(TAG, "addParticipant: failed")
            }
        }
    }

    fun removeParticipant(eventId: String, profile: UserProfile, onComplete: () -> Unit) {
        val eventDocRef = getEventDoc(eventId)
        val eventAttendingRef = authRepo.getCurrentlyAttendingEvent(eventId)
        firestore.runBatch { writeBatch ->
            writeBatch.update(eventDocRef, FIRE_PARTICIPANT_USERS, FieldValue.arrayRemove(profile))
            writeBatch.delete(eventAttendingRef)
        }.addOnCompleteListener {
            Log.d(TAG, "removeParticipant: ${if (it.isSuccessful) "" else "un"}successfully removed")
            if (it.isSuccessful) onComplete()
        }
    }

}