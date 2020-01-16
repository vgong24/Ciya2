package com.victoweng.ciya2.ui.message


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.messages.MessagesAdapter
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
    }

}
