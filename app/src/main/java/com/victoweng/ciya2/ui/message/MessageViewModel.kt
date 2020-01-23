package com.victoweng.ciya2.ui.message

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.victoweng.ciya2.data.chat.ChatMessage
import com.victoweng.ciya2.repository.message.MessagesRepo
import javax.inject.Inject

class MessageViewModel @Inject constructor(val messagesRepo: MessagesRepo) : ViewModel() {

    val messagesLiveData = MutableLiveData<MutableList<ChatMessage>>()

    private lateinit var listenerRegistration: ListenerRegistration

    fun addChatMessagesListener(channelId: String) {
        listenerRegistration = messagesRepo.listenForMessages(channelId) { onMessagesReceived(it) }
    }

    fun onMessagesReceived(messages: MutableList<ChatMessage>) {
        messagesLiveData.value = messages
    }

    fun observeMessagesLiveData(): LiveData<MutableList<ChatMessage>> {
        return messagesLiveData
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CLOWN", "oncleared")
        listenerRegistration.remove()
    }


    fun fetchAndUpdateMessages(roomId: String) {
        //get latest messageId from database
        //fetch from headend starting after latestMessageId
        //convert to MessageEntity
        //insert to database
        //fetch from database
        //display
    }

}