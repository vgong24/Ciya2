package com.victoweng.ciya2.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victoweng.ciya2.constants.FIRE_USER
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.UserProfile
import javax.inject.Inject

class AuthRepo @Inject constructor(val firebaseAuth: FirebaseAuth, val firebaseDatabase: FirebaseDatabase) {

    private val TAG = AuthRepo::class.java.canonicalName

    //FirebaseAuth
    fun getCurrentUser() = firebaseAuth.currentUser

    fun getCurrentUserId() = getCurrentUser()?.uid

    fun createCurrentUserProfile() =
        UserProfile(uid = getCurrentUserId()!!, userName = getCurrentUser()!!.displayName!!)

    fun isLoggedIn() = getCurrentUser() != null

    //FirebaseDatabase
    fun fetchUserInfo(uid: String, onSuccess: (UserProfile) -> Unit, onFailed: () -> Unit) {
        return firebaseDatabase.getReference(FIRE_USER)
            .child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(err: DatabaseError) {
                    Log.d(TAG, "fetchUserInfo: onCancelled ${err.message}");
                }

                override fun onDataChange(snapShot: DataSnapshot) {
                    if (snapShot.exists()) {
                        Log.d(TAG, "fetchUserInfo: onDataChange dataExists");
                        val userProfile = snapShot.getValue(UserProfile::class.java)
                        userProfile?.let {
                            onSuccess(it)
                            return
                        }
                    }
                    onFailed()
                }

            })
    }
}