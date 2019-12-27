package com.victoweng.ciya2.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.UserProfile

object FireStoreRepo {

    val fireStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun createUser(userProfile: UserProfile) : Task<Void>? {
        val ref = fireStore.collection("users")
            .document(FireRepo.getCurrentUserId()!!)
            .set(userProfile)
        return ref
    }



    fun fetchLocalEvents() : Task<QuerySnapshot>? {
        return fireStore.collection("eventDetails").get()
    }
}