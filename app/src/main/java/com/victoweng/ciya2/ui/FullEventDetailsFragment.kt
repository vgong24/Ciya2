package com.victoweng.ciya2.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.EventDetail
import kotlinx.android.synthetic.main.fragment_full_event_details.*

class FullEventDetailsFragment : Fragment(), OnMapReadyCallback {

    lateinit var eventDetail: EventDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_event_details, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(eventDetail.eventLocation.toLatLng()))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13f))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventDetail = arguments!!.getSerializable("eventDetail") as EventDetail

        val mapFragment = childFragmentManager.findFragmentById(R.id.event_location) as SupportMapFragment
        mapFragment.getMapAsync(this)

        event_title.text = eventDetail.title
        event_description.text = eventDetail.description
        event_time.text = eventDetail.eventLocation.toString()
    }


}
