package com.victoweng.ciya2.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.messages.ChatRoomsAdapter
import com.victoweng.ciya2.ui.viewmodels.MessageListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_messages_list.*

class MessagesListFragment : DaggerFragment() {

    val viewModel: MessageListViewModel by lazy {
        ViewModelProviders.of(this).get(MessageListViewModel::class.java)
    }

    val chatAdapter: ChatRoomsAdapter by lazy {
        ChatRoomsAdapter { chatRoom -> viewModel.onChatRoomClicked(chatRoom, findNavController()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chat_room_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = chatAdapter
        }

        viewModel.getChatRoomLiveData().observe(viewLifecycleOwner, Observer {
            chatAdapter.updateChatList(it)
        })

        viewModel.fetchChatRoomData()
    }


}
