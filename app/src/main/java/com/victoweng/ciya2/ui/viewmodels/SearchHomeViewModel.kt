package com.victoweng.ciya2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.repository.FireStoreRepo

class SearchHomeViewModel : ViewModel() {
    val localEventLiveData = MutableLiveData<List<EventDetail>>()

    fun getLocalEventList(): LiveData<List<EventDetail>> {
        return localEventLiveData
    }

    fun fetchLocalEvents() {
            FireStoreRepo.fetchLocalEvents()!!.addOnSuccessListener {document ->
                if (document != null) {
                    val results = document.toObjects(EventDetail::class.java)
                        localEventLiveData.value = results
                }
            }

    }



}