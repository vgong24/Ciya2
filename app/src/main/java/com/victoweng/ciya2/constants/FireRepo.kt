package com.victoweng.ciya2.constants

import com.google.firebase.auth.FirebaseAuth
import com.victoweng.ciya2.data.UserProfile

object FireRepo {

    val fireStoreInstance: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun getCurrentUser() = fireStoreInstance.currentUser

    fun getCurrentUserId() = getCurrentUser()?.uid

    fun createCurrentUserProfile() = UserProfile(getCurrentUserId()!!, getCurrentUser()!!.displayName!!)

}