package com.victoweng.ciya2.services

import android.os.Messenger
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)

    }

}