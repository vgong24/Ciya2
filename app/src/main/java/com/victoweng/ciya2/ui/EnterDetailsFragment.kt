package com.victoweng.ciya2.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.EventLocation
import kotlinx.android.synthetic.main.fragment_enter_details.*

class EnterDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val location = arguments!!.get("location") as EventLocation
        location_coordinates?.setText(location.toString())
    }


}
