package com.victoweng.ciya2.ui.message


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.messages.MessagesAdapter
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_message.*
import javax.inject.Inject

class MessageFragment : DaggerFragment() {

    @Inject
    lateinit var provider : ViewModelProviderFactory

    val viewModel : MessageViewModel by lazy {
        ViewModelProviders.of(this, provider).get(MessageViewModel::class.java)
    }

    val messageAdapter : MessagesAdapter by lazy {
        MessagesAdapter()
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
