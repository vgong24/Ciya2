package com.victoweng.ciya2.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.victoweng.ciya2.constants.FIRE_EVENTS_ATTENDING
import com.victoweng.ciya2.constants.FIRE_USER
import com.victoweng.ciya2.constants.FIRE_USERNAMES
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.UserProfile
import javax.inject.Inject

class AuthRepo @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firebaseDatabase: FirebaseDatabase,
    val fireStore: FirebaseFirestore
) {

    private val TAG = AuthRepo::class.java.canonicalName

    //FirebaseAuth
    fun getCurrentUser() = firebaseAuth.currentUser

    fun getCurrentUserId() = getCurrentUser()?.uid

    fun createCurrentUserProfile() =
        UserProfile(uid = getCurrentUserId()!!, userName = getCurrentUser()!!.displayName!!)

    fun isLoggedIn() = getCurrentUser() != null

    //FirebaseStore References
    fun getCurrentUserDocument() = fireStore.collection(FIRE_USER).document(getCurrentUserId()!!)

    fun getCurrentUserAttendingEventsCollection() = getCurrentUserDocument().collection(FIRE_EVENTS_ATTENDING)

    fun getCurrentlyAttendingEvent(eventId: String) = getCurrentUserAttendingEventsCollection().document(eventId)

    //FirebaseDatabase References
    fun getUserNameChildRef(name: String) = firebaseDatabase.getReference(FIRE_USERNAMES).child(name)

    //FirebaseDatabase functions
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

    //Joint functions
    fun updateUserProfile(userProfile: UserProfile) {
        fireStore.collection(FIRE_USER)
            .document(userProfile.uid)
            .set(userProfile)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "firestore updateUserProfile: Successful")
                } else {
                    Log.e(TAG, "firestore updateUserProfile: Unsuccessful")
                }
            }

        firebaseDatabase.getReference(FIRE_USER).child(userProfile.uid)
            .setValue(userProfile)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "firedatabase updateUserProfile: Successful")
                } else {
                    Log.e(TAG, "firedatabase updateUserProfile: Unsuccessful")
                }
            }
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userProfile.userName)
            .build()
        getCurrentUser()?.updateProfile(profileUpdates)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "Updated current profile DisplayName: ${profileUpdates.displayName}")
                }
            }
    }
}