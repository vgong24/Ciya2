package com.victoweng.ciya2.repository.event

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.victoweng.ciya2.constants.EVENT_GEO_FIRE
import com.victoweng.ciya2.constants.FIRE_EVENT_DETAILS
import com.victoweng.ciya2.constants.FIRE_HOST
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.repository.ChatMessagesAPI
import com.victoweng.ciya2.repository.auth.AuthRepo
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.extension.setLocation
import javax.inject.Inject

class EventApi @Inject constructor(val firestore: FirebaseFirestore, val chatMessagesAPI: ChatMessagesAPI, val authRepo: AuthRepo) {

    private val TAG = EventApi::class.java.canonicalName

    fun createGeoFire() = GeoFirestore(firestore.collection(EVENT_GEO_FIRE))

    fun createEventDetailReference() = firestore.collection(FIRE_EVENT_DETAILS).document()

    fun createEvent(eventDetail: EventDetail, eventDetailRef: DocumentReference, onComplete:(EventDetail) -> Unit) {
        val hostRef = authRepo.getCurrentUserDocument()
        val geoRef = createGeoFire()
        firestore.runBatch {
            writeBatch ->
            writeBatch.set(eventDetailRef, eventDetail)
            writeBatch.update(eventDetailRef, FIRE_HOST, hostRef)
            chatMessagesAPI.createChatRoom(eventDetail, writeBatch)
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                geoRef.setLocation(eventDetail.eventId, GeoPoint(eventDetail.eventLocation.lat, eventDetail.eventLocation.lon)) {
                    exception -> exception?.let { Log.d(TAG, "geoPoint location saved successfully to Firestore: $geoRef") }
                }
                onComplete(eventDetail)
            }
        }
    }

}