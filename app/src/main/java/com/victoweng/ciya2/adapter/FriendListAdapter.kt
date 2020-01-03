package com.victoweng.ciya2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.friend.FriendRequest
import kotlinx.android.synthetic.main.friend_item.view.*

class FriendListAdapter(val clickListener: (FriendRequest) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var friendList = mutableListOf<FriendRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context!!).inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(view)
    }

    fun updateFriendList(list: MutableList<FriendRequest>) {
        friendList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FriendViewHolder -> holder.onBind(friendList[position], clickListener)
        }
    }

    class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val friendName = view.friend_name
        val requestButton = view.request_button

        fun onBind(friendRequest: FriendRequest, clickListener: (FriendRequest) -> Unit) {
            friendName.setText(friendRequest.receiver.userName)
            requestButton.setText(friendRequest.requestState)
            requestButton.setOnClickListener { clickListener(friendRequest) }
        }
    }

}