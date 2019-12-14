package com.victoweng.ciya2.ui


import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.victoweng.ciya2.R
import com.victoweng.ciya2.ui.viewmodels.EnterDetailsViewModel
import kotlinx.android.synthetic.main.fragment_enter_details.*

class EnterDetailsFragment : Fragment() {

    val enterDetailsViewModel: EnterDetailsViewModel by lazy {
        ViewModelProvider(this).get(EnterDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterDetailsViewModel.setArguments(arguments!!)

        event_title.addTextChangedListener(afterTextChanged = {
            enterDetailsViewModel.setTitle(it.toString())
        })
        description_text.addTextChangedListener(afterTextChanged = {
            enterDetailsViewModel.setDescription(it.toString())
        })
        create_event_button.setOnClickListener {
           enterDetailsViewModel.createEvent()
        }
    }


}
