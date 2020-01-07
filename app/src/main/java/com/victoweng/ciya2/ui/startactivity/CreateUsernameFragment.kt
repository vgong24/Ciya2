package com.victoweng.ciya2.ui.startactivity


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.victoweng.ciya2.R
import com.victoweng.ciya2.ui.viewmodels.CreateUserNameViewModel
import com.victoweng.ciya2.ui.viewmodels.CreateUserNameViewModelFactory
import kotlinx.android.synthetic.main.fragment_create_username.*


class CreateUsernameFragment : Fragment() {

    val viewModel: CreateUserNameViewModel by lazy {
        ViewModelProviders.of(this, CreateUserNameViewModelFactory(context!!)).get(CreateUserNameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_username, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirm_button.setOnClickListener {
            viewModel.validateUserName(username_edittext.text.toString())
        }
    }


}
