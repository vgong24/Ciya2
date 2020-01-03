package com.victoweng.ciya2.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.AttendeeAdapter
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.ui.custom.JoinButton
import com.victoweng.ciya2.ui.viewmodels.FullEventDetailsViewModel
import kotlinx.android.synthetic.main.fragment_full_event_details.*

class FullEventDetailsFragment : Fragment(), OnMapReadyCallback {

    val TAG = FullEventDetailsFragment::class.java.canonicalName
    val viewModel : FullEventDetailsViewModel by lazy {
        ViewModelProvider(this).get(FullEventDetailsViewModel::class.java)
    }

    val attendeeAdapter : AttendeeAdapter by lazy {
        AttendeeAdapter{userProfile ->  viewModel.onAddButtonClicked(userProfile)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_event_details, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "event location: " + viewModel.eventDetailLiveData.value!!.eventLocation.toString())
        viewModel.eventDetailLiveData.observe(viewLifecycleOwner, Observer {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it.eventLocation.toLatLng()))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
            googleMap.uiSettings.isScrollGesturesEnabled = false
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event_attendees.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = attendeeAdapter
        }

        viewModel.getEventDetailsFrom(arguments!!)
        observeData()

        val mapFragment = childFragmentManager.findFragmentById(R.id.event_location) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    private fun observeData() {
        viewModel.eventDetailLiveData.observe(viewLifecycleOwner, Observer {
            event_title.text = it.title
            event_description.text = it.description + " " + it.eventLocation.toString()
            event_time.text = it.timeStampFormatted()

            viewModel.setupJoinButton(join_button, it)

            attendeeAdapter.setUsers(it.participants.userList)
        })
    }




}
