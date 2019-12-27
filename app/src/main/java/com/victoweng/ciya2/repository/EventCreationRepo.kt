package com.victoweng.ciya2.repository

import android.util.Log
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.EventDetail

object EventCreationRepo {

    fun createEvent(eventDetail: EventDetail) : Task<Void>? {
        val ref = FireStoreRepo.fireStore.collection("eventDetails").document();
        val hostRef = FireStoreRepo.fireStore.collection("users").document(FireRepo.getCurrentUserId()!!)
        val task = FireStoreRepo.fireStore.runBatch {
                writeBatch -> writeBatch.set(ref, eventDetail)
            writeBatch.update(ref, "host", hostRef)
        }

        val geoRef = FireDatabaseRepo.fireDatabase.getReference("eventGeoPath")
        val geoFire = GeoFire(geoRef)
        geoFire.setLocation(ref.id, GeoLocation(eventDetail.eventLocation.lat, eventDetail.eventLocation.lon), GeoFire.CompletionListener { key, error ->
          if (error != null) {
              Log.e("EventCreationRepo", "did not save... " + error.message)
          } else {
              Log.d("EventCreationRepo", "saved successfully")
          }
        })
        return task
    }

}