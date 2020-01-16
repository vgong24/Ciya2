package com.victoweng.ciya2.comparator

import com.victoweng.ciya2.data.chat.ChatRoom

class ChatRoomTimeComparator() : Comparator<ChatRoom> {

    override fun compare(p0: ChatRoom, p1: ChatRoom): Int {
        return p0.latestMessage.timeStamp.compareTo(p1.latestMessage.timeStamp)
    }

}