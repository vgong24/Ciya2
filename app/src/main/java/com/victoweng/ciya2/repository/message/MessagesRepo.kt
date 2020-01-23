package com.victoweng.ciya2.repository.message

import android.util.Log
import com.google.firebase.firestore.ListenerRegistration
import com.victoweng.ciya2.data.chat.ChatMessage
import com.victoweng.ciya2.repository.ChatMessagesAPI
import javax.inject.Inject

class MessagesRepo @Inject constructor(private val chatMessagesAPI: ChatMessagesAPI) {

    private val TAG = MessagesRepo::class.java.canonicalName

    fun listenForMessages(channelId: String, onListen: (MutableList<ChatMessage>) -> Unit): ListenerRegistration {
        return chatMessagesAPI.fetchMessages(channelId)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                Log.d(TAG, "listening for messages is empty: ${querySnapshot?.isEmpty}")
                if (firebaseFirestoreException != null) {
                    Log.e(TAG, "listenForMessageSnapshots: ${firebaseFirestoreException.message}")
                    return@addSnapshotListener
                }
                val items = mutableListOf<ChatMessage>()
                querySnapshot!!.documents.forEach {
                    val msg = it.toObject(ChatMessage::class.java)
                    msg?.let { it1 -> items.add(it1) }
                }
                onListen(items)
            }
    }

}