package com.victoweng.ciya2.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.CategoryType
import com.victoweng.ciya2.data.Location
import kotlinx.android.synthetic.main.fragment_choose_location.*


class ChooseLocationFragment : Fragment() {

    lateinit var categoryType: CategoryType
    lateinit var location: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        location = Location(0,0)
        categoryType = arguments!!.getSerializable("categoryType") as CategoryType
        location_next_button.setOnClickListener {
            val bundle = bundleOf(
                "categoryType" to categoryType,
                "location" to location
            )

            Navigation.findNavController(view).navigate(R.id.action_chooseLocationFragment_to_enterDetailsFragment, bundle)
        }
    }


}
