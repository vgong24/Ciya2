package com.victoweng.ciya2.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.FireStoreRepo
import com.victoweng.ciya2.util.ToastUtil

class CreateUserNameViewModel(val context: Context) : ViewModel() {
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
                        mutableData.value = FireRepo.getCurrentUserId()
                        val userProfile =
                            UserProfile(FireRepo.getCurrentUserId()!!, FireRepo.getCurrentUser()?.email!!, userName)
                        usersRef.child(FireRepo.getCurrentUserId()!!)
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
                        ToastUtil.show(context, "Committed ${snapshot.toString()}")
                    } else {
                        ToastUtil.show(context, "Exists try enter another username")
                    }
                    isValidating = false
                }
            })
    }
}

class CreateUserNameViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateUserNameViewModel(context) as T
    }

}