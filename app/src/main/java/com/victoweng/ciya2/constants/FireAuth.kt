package com.victoweng.ciya2.constants

import com.google.firebase.auth.FirebaseAuth
import com.victoweng.ciya2.data.UserProfile

object FireAuth {

    val fireStoreAuthInstance: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun getCurrentUser() = fireStoreAuthInstance.currentUser

    fun getCurrentUserId() = getCurrentUser()?.uid

    fun createCurrentUserProfile() = UserProfile(uid = getCurrentUserId()!!, userName = getCurrentUser()!!.displayName!!)

}