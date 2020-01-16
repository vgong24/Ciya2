package com.victoweng.ciya2.ui.searchEvent


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.AttendeeAdapter
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.repository.auth.AuthRepo
import com.victoweng.ciya2.ui.viewmodels.FullEventDetailsViewModel
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_full_event_details.*
import javax.inject.Inject

class FullEventDetailsFragment : DaggerFragment(), OnMapReadyCallback {

    val TAG = FullEventDetailsFragment::class.java.canonicalName

    @Inject
    lateinit var authRepo: AuthRepo

    @Inject
    lateinit var provider : ViewModelProviderFactory

    val viewModel: FullEventDetailsViewModel by lazy {
        ViewModelProvider(this, provider).get(FullEventDetailsViewModel::class.java)
    }

    val attendeeAdapter: AttendeeAdapter by lazy {
        AttendeeAdapter(authRepo) { userProfile -> viewModel.onAddButtonClicked(userProfile) }
    }

    lateinit var gMap: GoogleMap
    var markerPoint: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_event_details, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        Log.d(TAG, "event location: " + viewModel.eventDetailLiveData.value!!.eventLocation.toString())
        viewModel.eventDetailLiveData.observe(viewLifecycleOwner, Observer {
            markerPoint = googleMap.addMarker(MarkerOptions().position(it.eventLocation.toLatLng()))
            setMarkerVisibility(it)

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it.eventLocation.toLatLng()))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
            googleMap.uiSettings.isScrollGesturesEnabled = false
        })
    }

    private fun setMarkerVisibility(it: EventDetail) {
        markerPoint?.isVisible = it.participants.containsUser(authRepo.getCurrentUserId()!!)
        Log.d("CLOWN", "Set markerVisibility... ${markerPoint?.isVisible}")
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
        viewModel.getEventDetailLiveData().observe(viewLifecycleOwner, Observer {
            event_title.text = it.title
            event_description.text = it.description + " " + it.eventLocation.toString()
            event_time.text = it.timeStampFormatted()

            viewModel.setupJoinButton(join_button, it)
            attendeeAdapter.setUsers(it.participants.userList)
            setMarkerVisibility(it)
        })
    }


}
