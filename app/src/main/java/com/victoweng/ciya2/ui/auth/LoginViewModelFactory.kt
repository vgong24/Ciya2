package com.victoweng.ciya2.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class LoginViewModelFactory(val context: Context, val navController: NavController) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(context, navController) as T
    }

}