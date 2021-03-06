package com.victoweng.ciya2.ui.searchEvent

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.SearchAdapter
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_home.*
import javax.inject.Inject

class SearchHomeFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    val TAG = SearchHomeFragment::class.java.canonicalName
    private val MY_PERMISSION_ACCESS_COURSE_LOCATION = 11

    val viewModel : SearchHomeViewModel by lazy {
        ViewModelProviders.of(this, providerFactory).get(SearchHomeViewModel::class.java)
    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter{eventDetail -> eventItemClicked(eventDetail) }
    }

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)
        return inflater.inflate(R.layout.fragment_search_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //check authentication
        val navController = findNavController()
        if (FirebaseAuth.getInstance().currentUser == null) {
            Log.d(TAG, "Switching to loginFragment cuz we not logged in")
            navController.navigate(R.id.action_searchHomeFragment_to_loginFragment)
        } else {
            Log.d(TAG, "logged in...")
        }

        checkLocationPermissions()
    }

    private fun checkLocationPermissions() {
        if (!isLocationPermissionsGranted()) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSION_ACCESS_COURSE_LOCATION
            )
        } else {
            setupFragment()
        }
    }

    private fun isLocationPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            MY_PERMISSION_ACCESS_COURSE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupFragment()
                } else {
                    //navigate back
                    Log.e(TAG, "onRequestPermissionResult access denied. Navigate back")
                    checkLocationPermissions()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupFragment() {
        search_recyclerview.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        viewModel.getLocalEventList().observe(viewLifecycleOwner, Observer {
            searchAdapter.setEventList(it)
        })

        if (isLocationPermissionsGranted()) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if(location != null) {
                        viewModel.fetchLocalEvents(location)
                    }
                }
        }


        create_event.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_searchHomeFragment_to_eventCreationFragment)
        }
    }

    private fun eventItemClicked(eventDetails: EventDetail) {
        //Go to event details screen
        viewModel.goToEventDetailsScreen(eventDetails, findNavController())
        Log.d(TAG, "event item clicked!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SearchHomeFragment ondestroy")
    }


}
