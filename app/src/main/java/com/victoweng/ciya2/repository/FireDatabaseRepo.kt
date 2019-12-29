package com.victoweng.ciya2.repository

import com.firebase.geofire.GeoFire
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.victoweng.ciya2.constants.EVENT_GEO_FIRE
import com.victoweng.ciya2.constants.FireRepo

object FireDatabaseRepo {
    val fireDatabase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    fun getUser(userId: String) = fireDatabase.getReference("users")
        .child(userId)

    fun getGeoFireReference() : GeoFire {
        return GeoFire(fireDatabase.getReference(EVENT_GEO_FIRE))
    }
}