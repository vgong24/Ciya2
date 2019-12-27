package com.victoweng.ciya2.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.AttendeeAdapter
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.ui.viewmodels.FullEventDetailsViewModel
import com.victoweng.ciya2.util.date.DateTimeUtil
import kotlinx.android.synthetic.main.fragment_full_event_details.*

class FullEventDetailsFragment : Fragment(), OnMapReadyCallback {

    val viewModel : FullEventDetailsViewModel by lazy {
        ViewModelProvider(this).get(FullEventDetailsViewModel::class.java)
    }

    val attendeeAdapter : AttendeeAdapter by lazy {
        AttendeeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_event_details, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("CLOWN", "event location: " + viewModel.eventDetailLiveData.value!!.eventLocation.toString())
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(viewModel.getEventLocation()))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13f))
        googleMap.uiSettings.isScrollGesturesEnabled = false
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
            event_description.text = it.description
            event_time.text = it.timeStampFormatted()
            attendeeAdapter.setUsers(it.participants.userList)
        })
    }


}
