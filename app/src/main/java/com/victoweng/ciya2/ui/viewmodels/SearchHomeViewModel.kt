package com.victoweng.ciya2.ui.viewmodels

import android.location.Location
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.EVENT_DETAIL
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.repository.FireStoreRepo

class SearchHomeViewModel : ViewModel() {
    val localEventLiveData = MutableLiveData<List<EventDetail>>()

    fun getLocalEventList(): LiveData<List<EventDetail>> {
        return localEventLiveData
    }

    fun fetchLocalEvents(location: Location) {
            FireStoreRepo.fetchLocalEvents(location, {list -> updateLocalEvents(list) })

//        !!.addOnSuccessListener {document ->
//                if (document != null) {
//                    val results = document.toObjects(EventDetail::class.java)
//                        localEventLiveData.value = results
//                }
//            }
    }

    fun updateLocalEvents(result: MutableList<EventDetail>) {
        localEventLiveData.value = result
    }

    fun goToEventDetailsScreen(eventDetail: EventDetail, navController: NavController) {
        val bundle = bundleOf(
            EVENT_DETAIL to eventDetail
        )
        navController.navigate(R.id.action_searchHomeFragment_to_fullEventDetails, bundle)
    }



}