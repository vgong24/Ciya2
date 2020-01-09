package com.victoweng.ciya2.ui.viewmodels

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.chat.ChatRoom
import com.victoweng.ciya2.repository.ChatMessagesRepo

class MessageListViewModel : ViewModel() {

    val chatRoomLiveData = MutableLiveData<MutableList<ChatRoom>>()

    fun fetchChatRoomData() {
        ChatMessagesRepo.fetchChatRoomsFor {chatRoomList -> updateChatRoomLiveData(chatRoomList)}
    }

    fun onChatRoomClicked(chatRoom: ChatRoom, navController: NavController) {
        val bundle = bundleOf(
            "chatRoom" to chatRoom
        )
        navController.navigate(R.id.action_messagesListFragment_to_messageFragment, bundle)
    }

    fun updateChatRoomLiveData(mutableList: MutableList<ChatRoom>) {
        chatRoomLiveData.value = mutableList
    }

    fun getChatRoomLiveData() : LiveData<MutableList<ChatRoom>> {
        return chatRoomLiveData
    }

}