package com.victoweng.ciya2.ui


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.EVENT_CATEGORY_TYPE
import com.victoweng.ciya2.constants.EVENT_LOCATION
import com.victoweng.ciya2.data.CategoryType
import com.victoweng.ciya2.data.EventLocation
import com.victoweng.ciya2.ui.viewmodels.ChooseLocationViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_choose_location.*

class ChooseLocationFragment : DaggerFragment(), OnMapReadyCallback {

    val viewModel : ChooseLocationViewModel by lazy {
        ViewModelProviders.of(this).get(ChooseLocationViewModel::class.java)
    }

    val MY_PERMISSION_ACCESS_COURSE_LOCATION = 11
    lateinit var categoryType: CategoryType
    lateinit var eventLocation: EventLocation
    lateinit var googleMap: GoogleMap
    lateinit var fusedLocationClient: FusedLocationProviderClient
    val TAG = ChooseLocationFragment::class.java.canonicalName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        return inflater.inflate(R.layout.fragment_choose_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLocationPermissions()
    }

    private fun setupFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.select_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        categoryType = arguments!!.getParcelable("categoryType") as CategoryType

        location_next_button.setOnClickListener {
            val latLng = googleMap.cameraPosition.target
            eventLocation = EventLocation(latLng.latitude, latLng.longitude)

            val bundle = bundleOf(
                EVENT_CATEGORY_TYPE to categoryType,
                EVENT_LOCATION to eventLocation
            )

            Navigation.findNavController(view!!)
                .navigate(R.id.action_chooseLocationFragment_to_enterDetailsFragment, bundle)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            MY_PERMISSION_ACCESS_COURSE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupFragment()
                } else {
                    //navigate back
                    Log.e(TAG, "onRequestPermissionResult access denied. Navigate back")
                    Navigation.findNavController(view!!)
                        .navigate(R.id.searchHomeFragment)
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onMapReady permissionsDenied. Don't locate users current location." )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                location -> zoomIntoLocation(location)
            }
    }

    fun zoomIntoLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        Log.d(TAG, "zoomIntoLocation latlng " + latLng.toString())
        googleMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f))
    }


    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSION_ACCESS_COURSE_LOCATION
            )
        } else {
            setupFragment()
        }
    }

}


