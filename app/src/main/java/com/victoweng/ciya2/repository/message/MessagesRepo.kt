package com.victoweng.ciya2.repository.message

import com.victoweng.ciya2.repository.ChatMessagesAPI
import com.victoweng.ciya2.repository.database.messages.MessageDao
import javax.inject.Inject

class MessagesRepo @Inject constructor(val messageDao: MessageDao, val chatMessagesAPI: ChatMessagesAPI) {

}