package com.victoweng.ciya2.ui.message


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.messages.MessagesAdapter
import com.victoweng.ciya2.constants.FIRE_CHAT_ROOM
import com.victoweng.ciya2.data.chat.ChatRoom
import com.victoweng.ciya2.repository.auth.AuthRepo
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_message.*
import javax.inject.Inject

class MessageFragment : DaggerFragment() {

    @Inject
    lateinit var provider: ViewModelProviderFactory

    @Inject
    lateinit var authRepo: AuthRepo

    val viewModel: MessageViewModel by lazy {
        ViewModelProviders.of(this, provider).get(MessageViewModel::class.java)
    }

    val messageAdapter: MessagesAdapter by lazy {
        MessagesAdapter(authRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        message_list_recyclerview.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(context)
        }
        val chatRoom = arguments!!.get(FIRE_CHAT_ROOM) as ChatRoom

        viewModel.addChatMessagesListener(chatRoom.roomId)

        viewModel.observeMessagesLiveData().observe(viewLifecycleOwner, Observer {
            Log.d("CLOWN", "update message ${it.size}");
            messageAdapter.updateMessages(it)
        })
    }

}
