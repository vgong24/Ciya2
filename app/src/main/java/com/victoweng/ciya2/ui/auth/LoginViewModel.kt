package com.victoweng.ciya2.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.FireDatabaseRepo
import com.victoweng.ciya2.util.ToastUtil
import javax.inject.Inject

class LoginViewModel @Inject constructor(val toastUtil: ToastUtil) : ViewModel() {

    private val navigationActionLiveData = MutableLiveData<Int>()

    val TAG = LoginViewModel::class.java.canonicalName
    fun handleAuthentication(result: Task<AuthResult>) {
        if (result.isSuccessful) {
            Log.d(TAG, "is successful")
            userHasUserName()
        } else {
            toastUtil.show("Sign in failed after auth " + result.exception?.message)
        }
    }

    fun observeNavigationAction(): LiveData<Int> {
        return navigationActionLiveData
    }

    fun userHasUserName() {
        FireDatabaseRepo.getUser(FireAuth.getCurrentUserId()!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userProfile = dataSnapshot.getValue(UserProfile::class.java)
                        if (userProfile?.userName != null) {
                            navigateTo(R.id.action_loginFragment_to_searchHomeFragment)
                        } else {
                            toastUtil.show("Create username...")
                            navigateTo(R.id.action_loginFragment_to_createUsernameFragment)
                        }
                    } else {
                        Log.d(TAG, "doesnt exist...")
                        toastUtil.show("Create username...")
                        navigateTo(R.id.action_loginFragment_to_createUsernameFragment)
                    }
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })
        Log.d(TAG, "check databaseRef")
    }

    private fun navigateTo(actionId: Int) {
        navigationActionLiveData.value = actionId
    }
}

