package com.victoweng.ciya2.ui.message

import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.repository.message.MessagesRepo
import javax.inject.Inject

class MessageViewModel @Inject constructor(val messagesRepo: MessagesRepo) : ViewModel() {

    fun fetchAndUpdateMessages(roomId: String) {
        //get latest messageId from database
        //fetch from headend starting after latestMessageId
        //convert to MessageEntity
        //insert to database
        //fetch from database
        //display
    }

}