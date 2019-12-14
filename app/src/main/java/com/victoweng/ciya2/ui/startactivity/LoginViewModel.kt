package com.victoweng.ciya2.ui.startactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        UNAUTHENTICATED,        //Initial State
        AUTHENTICATED,          //Successful authentication
        INVALID_AUTHENTICATION  //Failed authentication
    }

    val authenticationState = MutableLiveData<AuthenticationState>()
}
