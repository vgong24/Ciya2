package com.victoweng.ciya2.ui.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.victoweng.ciya2.R
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_create_username.*
import javax.inject.Inject


class CreateUsernameFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    val viewModel: CreateUserNameViewModel by lazy {
        ViewModelProviders.of(
            this,
            providerFactory
        ).get(CreateUserNameViewModel::class.java)
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

        viewModel.observeUserCreated().observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_createUsernameFragment_to_searchHomeFragment)
            }
        })

        confirm_button.setOnClickListener {
            viewModel.validateUserName(username_edittext.text.toString())
        }
    }


}
