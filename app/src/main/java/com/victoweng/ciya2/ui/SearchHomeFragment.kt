package com.victoweng.ciya2.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.SearchAdapter
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.ui.viewmodels.SearchHomeViewModel
import kotlinx.android.synthetic.main.fragment_search_home.*


class SearchHomeFragment : Fragment() {

    val viewModel : SearchHomeViewModel by lazy {
        ViewModelProviders.of(this).get(SearchHomeViewModel::class.java)
    }

    val searchAdapter: SearchAdapter by lazy {
        SearchAdapter{eventDetail -> eventItemClicked(eventDetail) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check authentication
        val navController = findNavController()
        if (FirebaseAuth.getInstance().currentUser == null) {
            Log.d("CLOWN", "Switchinig to loginFragment cuz we not logged in")
            navController.navigate(R.id.loginFragment)
        } else {
            Log.d("CLOWN", "logged in...")
        }

        search_recyclerview.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        viewModel.getLocalEventList().observe(viewLifecycleOwner, Observer {
            searchAdapter.setEventList(it)
        })

        viewModel.fetchLocalEvents()

        create_event.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_searchHomeFragment_to_eventCreationFragment)
        }
    }

    fun eventItemClicked(eventDetails: EventDetail) {
        //Go to event details screen
        viewModel.goToEventDetailsScreen(eventDetails, findNavController())
        Log.d("CLOWN", "event item clicked!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CLOWN", "SearchHomeFragment ondestroy")
    }


}
