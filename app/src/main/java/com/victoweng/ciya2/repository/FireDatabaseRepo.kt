package com.victoweng.ciya2.repository

import com.google.firebase.database.FirebaseDatabase
import com.victoweng.ciya2.constants.FireRepo

object FireDatabaseRepo {
    val fireDatabase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    fun getUser(userId: String) = fireDatabase.getReference("users")
        .child(userId)

}