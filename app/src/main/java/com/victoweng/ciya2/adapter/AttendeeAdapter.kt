package com.victoweng.ciya2.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.UserProfile
import kotlinx.android.synthetic.main.attendee_item.view.*

class AttendeeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val users = MutableLiveData<List<UserProfile>>(ArrayList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context!!).inflate(R.layout.attendee_item, parent, false)
        return AttendeeViewHolder(view)
    }

    fun setUsers(userList: List<UserProfile>) {
        Log.d("CLOWN", "setusers " + userList.size)
        users.value = userList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is AttendeeViewHolder -> holder.onBind(users.value!!.get(position))
        }
    }

    override fun getItemCount(): Int {
        return users.value!!.size
    }

    class AttendeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage = view.profile_image
        val userNameText = view.username_textview
        val addButton = view.add_friend

        fun onBind(userProfile: UserProfile) {
            userNameText.text = userProfile.userName
            Log.d("CLOWN", "bound usernametext " + userProfile.userName)
            addButton.isEnabled = FireRepo.getCurrentUserId() != userProfile.uid
        }

    }
}