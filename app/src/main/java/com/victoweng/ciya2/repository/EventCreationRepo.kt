package com.victoweng.ciya2.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.GeoPoint
import com.victoweng.ciya2.constants.FIRE_EVENT_DETAILS
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.EventDetail
import org.imperiumlabs.geofirestore.extension.setLocation

object EventCreationRepo {
    val TAG = EventCreationRepo::class.java.canonicalName
    /**
     * Saves the eventDetail with the host reference and the eventId from the document()
     * @param eventDetail
     */
    fun createEvent(eventDetail: EventDetail) : Task<Void>? {
        val ref = FireStoreRepo.fireStore.collection(FIRE_EVENT_DETAILS).document()
        eventDetail.eventId = ref.id
        val hostRef = FireStoreRepo.fireStore.collection("users").document(FireRepo.getCurrentUserId()!!)
        val geoRef = FireStoreRepo.geoFireStore
        val task = FireStoreRepo.fireStore.runBatch {
            writeBatch ->
            writeBatch.set(ref, eventDetail)
            writeBatch.update(ref, "host", hostRef)
        }
        geoRef.setLocation(ref.id, GeoPoint(eventDetail.eventLocation.lat, eventDetail.eventLocation.lon)) { exception ->
            if (exception != null) {
                Log.d(TAG, "geoPoint location saved successfully to firestore")
            }
        }

        return task
    }

}