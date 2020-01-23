package com.victoweng.ciya2.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.victoweng.ciya2.repository.auth.AuthRepo
import dagger.android.AndroidInjection
import javax.inject.Inject

class MessagingService : FirebaseMessagingService() {

    private val TAG = MessagingService::class.java.canonicalName

    @Inject
    lateinit var authRepo: AuthRepo

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        Log.d(TAG, "Messaging Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy");
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("CLOWN", "on new Token: $token")
        authRepo.addTokenToFirestore(token)
    }

}