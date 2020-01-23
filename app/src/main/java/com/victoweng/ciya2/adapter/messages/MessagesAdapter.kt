package com.victoweng.ciya2.adapter.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.chat.ChatMessage
import com.victoweng.ciya2.repository.auth.AuthRepo

class MessagesAdapter(val authRepo: AuthRepo) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val RECEIVER_TYPE = 1
    private val SENDER_TYPE = 2

    private var messagesList = mutableListOf<ChatMessage>()

    fun updateMessages(list: MutableList<ChatMessage>) {
        val diffResult = DiffUtil.calculateDiff(MessagesDiffUtil(messagesList, list))
        messagesList = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RECEIVER_TYPE -> return ReceiverViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_message_received,
                    parent,
                    false
                )
            )
        }
        return SenderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_sent, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        val message = messagesList[position]
        return if (message.userProfile.uid == authRepo.getCurrentUserId()) {
            SENDER_TYPE
        } else {
            RECEIVER_TYPE
        }
    }

    override fun getItemCount() = messagesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReceiverViewHolder -> holder.onBind(messagesList[position])
            is SenderViewHolder -> holder.onBind(messagesList[position])
        }
    }

    class MessagesDiffUtil(val oldList: MutableList<ChatMessage>, val newList: MutableList<ChatMessage>) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].mesId == newList[newItemPosition].mesId

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
