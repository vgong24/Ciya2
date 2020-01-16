package com.victoweng.ciya2.ui.auth

import android.content.Context
import android.content.Intent
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
import com.victoweng.ciya2.repository.auth.AuthRepo
import com.victoweng.ciya2.util.ToastUtil
import javax.inject.Inject

class LoginViewModel @Inject constructor(val toastUtil: ToastUtil, val authRepo: AuthRepo) : ViewModel() {

    private val TAG = LoginViewModel::class.java.canonicalName

    private val navigationActionLiveData = MutableLiveData<Int>()
    private val shouldShowSignInButtonLiveData = MutableLiveData<Boolean>(true)

    fun checkForAuthentication() {
        if (authRepo.isLoggedIn()) {
            navigateTo(R.id.action_loginFragment_to_searchHomeFragment)
        }
    }

    fun observeNavigationAction(): LiveData<Int> {
        return navigationActionLiveData
    }

    fun observeShouldShowSignInButton(): LiveData<Boolean> {
        return shouldShowSignInButtonLiveData
    }

    fun userHasUserName() {
        shouldShowSignInButtonLiveData.value = false
        authRepo.fetchUserInfo(authRepo.getCurrentUserId()!!, onSuccess = {navigateBasedOnUserInfo(it)}, onFailed = {navigateBasedOnUserInfo(null)})
    }

    private fun navigateBasedOnUserInfo(userProfile: UserProfile?) {
        Log.d(TAG, "navigateBasedOnUserInfo: ${userProfile?.userName}");
        userProfile?.userName?.let {
            navigateTo(R.id.action_loginFragment_to_searchHomeFragment)
        } ?: navigateTo(R.id.action_loginFragment_to_createUsernameFragment)
    }

    private fun navigateTo(actionId: Int) {
        navigationActionLiveData.value = actionId
    }
}

