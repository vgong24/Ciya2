package com.victoweng.ciya2.ui.eventcreation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.CategoryAdapter
import com.victoweng.ciya2.data.CategoryType
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_event_creation.*

class EventCreationFragment : DaggerFragment() {

    lateinit var viewModel: EventCreationViewModel
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventCreationViewModel::class.java)

        category_creation_recyclerview.apply {
            layoutManager = GridLayoutManager(context, 2)
            categoryAdapter = CategoryAdapter { categoryType : CategoryType -> categoryItemClicked(categoryType)}
            adapter = categoryAdapter
        }

        viewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            types -> categoryAdapter.updateList(types)
            println("debug: list retrieved " + types.size)
        })
        viewModel.categoryLiveData
    }

    private fun categoryItemClicked(categoryType: CategoryType) {
        this.view?.let {
            val bundle = bundleOf(
                "categoryType" to categoryType
            )
            Navigation.findNavController(it).navigate(
                R.id.action_eventCreationFragment_to_chooseLocationFragment,
                bundle
            )
        } ?: run { Log.d(EventCreationFragment::class.java.canonicalName, "failed to click on item") }
    }


}
