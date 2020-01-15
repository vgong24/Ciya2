package com.victoweng.ciya2.ui.message

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.chat.ChatRoom
import com.victoweng.ciya2.repository.ChatMessagesAPI
import javax.inject.Inject

class MessageListViewModel @Inject constructor(val chatAPI: ChatMessagesAPI) : ViewModel() {

    val chatRoomLiveData = MutableLiveData<MutableList<ChatRoom>>()

    fun fetchChatRoomData() {
        chatAPI.fetchChatRoomsFor { chatRoomList -> updateChatRoomLiveData(chatRoomList)}
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