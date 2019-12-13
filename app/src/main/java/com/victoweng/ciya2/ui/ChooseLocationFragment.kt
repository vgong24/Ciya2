package com.victoweng.ciya2.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.CategoryType
import com.victoweng.ciya2.data.EventLocation
import kotlinx.android.synthetic.main.fragment_choose_location.*

class ChooseLocationFragment : Fragment(), OnMapReadyCallback {

    lateinit var categoryType: CategoryType
    lateinit var eventLocation: EventLocation
    lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.select_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        categoryType = arguments!!.getSerializable("categoryType") as CategoryType

        location_next_button.setOnClickListener {
            val latLng = googleMap.cameraPosition.target
            eventLocation = EventLocation(latLng.latitude, latLng.longitude)

            val bundle = bundleOf(
                "categoryType" to categoryType,
                "location" to eventLocation
            )

            Navigation.findNavController(view)
                .navigate(R.id.action_chooseLocationFragment_to_enterDetailsFragment, bundle)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
    }


}
