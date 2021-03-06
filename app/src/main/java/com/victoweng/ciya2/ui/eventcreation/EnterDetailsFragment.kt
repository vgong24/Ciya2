package com.victoweng.ciya2.ui.eventcreation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.victoweng.ciya2.R
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import com.victoweng.ciya2.util.date.DateTimeUtil
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_enter_details.*
import javax.inject.Inject

class EnterDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    val enterDetailsViewModel: EnterDetailsViewModel by lazy {
        ViewModelProvider(this, providerFactory).get(EnterDetailsViewModel::class.java)
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

        enterDetailsViewModel.getDateLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            event_date_and_time.setText(DateTimeUtil.asString(it.build()))
        })

        event_title.addTextChangedListener(afterTextChanged = {
            enterDetailsViewModel.setTitle(it.toString())
        })
        description_text.addTextChangedListener(afterTextChanged = {
            enterDetailsViewModel.setDescription(it.toString())
        })
        event_date_and_time.setOnClickListener {
            enterDetailsViewModel.showDatePicker(context!!)
        }

        create_event_button.setOnClickListener {
           enterDetailsViewModel.createEvent(findNavController())
        }
    }

}
