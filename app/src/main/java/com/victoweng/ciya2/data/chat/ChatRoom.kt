package com.victoweng.ciya2.data.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatRoom(val roomId: String = "",
                    val title: String = "",
                    val latestMessage: ChatMessage = ChatMessage()) : Parcelable

//Will have sub collection messages through document(roomId).collection(messages)
