package com.victoweng.ciya2.data.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatRoom(
    val roomId: String = "",
    val title: String = "",
    val latestMessage: ChatMessage = ChatMessage()
) : Parcelable {
    override fun toString(): String {
        return "$roomId $title latestMessage: ${latestMessage.message} from ${latestMessage.userProfile.userName}"
    }
}

//Will have sub collection messages through document(roomId).collection(messages)
