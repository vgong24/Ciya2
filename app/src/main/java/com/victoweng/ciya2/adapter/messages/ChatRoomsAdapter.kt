package com.victoweng.ciya2.adapter.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.chat.ChatRoom
import com.victoweng.ciya2.util.date.DateTimeUtil
import kotlinx.android.synthetic.main.item_group_message.view.*

class ChatRoomsAdapter(val clickListener: (ChatRoom) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var chatRooms = mutableListOf<ChatRoom>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_group_message, parent, false)
        return GroupChatViewHolder(view)
    }

    fun updateChatList(list: MutableList<ChatRoom>) {
        val diffResult = DiffUtil.calculateDiff(ChatRoomsDiffUTil(list, chatRooms))
        chatRooms = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return chatRooms.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is GroupChatViewHolder -> holder.onBind(chatRooms[position], clickListener)
        }
    }

    class GroupChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle = view.text_event_title
        val messagePreview = view.text_message_preview
        val eventImage = view.image_event_pic
        val eventTime = view.text_event_time

        fun onBind(chatRoom: ChatRoom, clickListener: (ChatRoom) -> Unit) {
            eventTitle.text = chatRoom.title
            messagePreview.text = chatRoom.latestMessage.message
            eventTime.text = DateTimeUtil.asChatString(chatRoom.latestMessage.timeStamp)
            itemView.setOnClickListener { clickListener(chatRoom) }
        }
    }

    class ChatRoomsDiffUTil(val newList: MutableList<ChatRoom>, val oldList: MutableList<ChatRoom>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].roomId == newList[newItemPosition].roomId
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].equals(newList[newItemPosition])
        }

    }

}