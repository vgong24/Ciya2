package com.victoweng.ciya2.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.auth.AuthRepo
import com.victoweng.ciya2.util.ToastUtil
import javax.inject.Inject

class CreateUserNameViewModel @Inject constructor(private val toastUtil: ToastUtil, val authRepo: AuthRepo) :
    ViewModel() {
    private val TAG = CreateUserNameViewModel::class.java.canonicalName
    private var isValidating = false

    private val onUserCreatedLiveData = MutableLiveData<Boolean>()

    fun observeUserCreated(): LiveData<Boolean> {
        return onUserCreatedLiveData
    }

    fun validateUserName(userName: String) {
        Log.d(TAG, "validating username")
        if (isValidating) {
            return
        }
        isValidating = true
        authRepo.getUserNameChildRef(userName)
            .runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    if (mutableData.getValue() == null) {
                        mutableData.value = authRepo.getCurrentUserId()
                        val userProfile =
                            UserProfile(authRepo.getCurrentUserId()!!, authRepo.getCurrentUser()?.email!!, userName)

                        authRepo.updateUserProfile(userProfile)

                        return Transaction.success(mutableData)
                    }
                    return Transaction.abort()
                }

                override fun onComplete(error: DatabaseError?, commited: Boolean, snapshot: DataSnapshot?) {
                    onUserCreatedLiveData.value = commited
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