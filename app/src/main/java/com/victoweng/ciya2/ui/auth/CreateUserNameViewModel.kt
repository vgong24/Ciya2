package com.victoweng.ciya2.ui.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.FireStoreRepo
import com.victoweng.ciya2.util.ToastUtil
import javax.inject.Inject

class CreateUserNameViewModel @Inject constructor(private val toastUtil: ToastUtil, private val context: Context) :
    ViewModel() {
    val TAG = CreateUserNameViewModel::class.java.canonicalName
    val usernamesRef = FirebaseDatabase.getInstance().getReference("usernames")
    val usersRef = FirebaseDatabase.getInstance().getReference("users")
    var isValidating = false

    fun validateUserName(userName: String) {
        Log.d(TAG, "validating username")
        if (isValidating) {
            return
        }
        isValidating = true
        usernamesRef.child(userName)
            .runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    if (mutableData.getValue() == null) {
                        mutableData.value = FireAuth.getCurrentUserId()
                        val userProfile =
                            UserProfile(FireAuth.getCurrentUserId()!!, FireAuth.getCurrentUser()?.email!!, userName)
                        usersRef.child(FireAuth.getCurrentUserId()!!)
                            .setValue(userProfile)
                            .addOnSuccessListener {
                                Log.d(TAG, "user with has been added under username $userName")
                            }
                        FireStoreRepo.createUser(userProfile)
                            ?.addOnSuccessListener {
                                Log.d(TAG, "UserNameCreated in firestore")
                            }
                        return Transaction.success(mutableData)
                    }
                    return Transaction.abort()
                }

                override fun onComplete(error: DatabaseError?, commited: Boolean, snapshot: DataSnapshot?) {
                    if (commited) {
                        toastUtil.show("Committed ${snapshot.toString()}")
                    } else {
                        toastUtil.show("Exists try enter another username")
                    }
                    isValidating = false
                }
            })
    }
}