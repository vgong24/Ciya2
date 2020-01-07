package com.victoweng.ciya2.adapter.messages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.data.chat.ChatMessage
import com.victoweng.ciya2.util.date.DateTimeUtil
import kotlinx.android.synthetic.main.item_message_received.view.*

class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textName = view.text_message_name
    val textBody = view.text_message_body
    val imageProfile = view.image_message_profile
    val textTime = view.text_message_time

    fun onBind(chatMessage: ChatMessage) {
        textName.text = chatMessage.userProfile.userName
        textBody.text = chatMessage.message
        textTime.text = DateTimeUtil.asChatString(chatMessage.timeStamp)
    }
}