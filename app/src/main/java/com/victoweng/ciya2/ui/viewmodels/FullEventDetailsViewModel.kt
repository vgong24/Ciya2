package com.victoweng.ciya2.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.constants.EVENT_DETAIL
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.EventLocation

class FullEventDetailsViewModel : ViewModel() {

    val locationLiveData = MutableLiveData<EventLocation>(EventLocation())
    val eventDetailLiveData = MutableLiveData<EventDetail>()

    fun getEventDetailsFrom(bundle: Bundle) {
        val detail = bundle.getSerializable(EVENT_DETAIL) as EventDetail
        updateEventDetails(detail)
    }

    fun updateEventDetails(eventDetail: EventDetail?) {
        eventDetailLiveData.value = eventDetail
    }

    fun getEventLocation() = locationLiveData.value!!.toLatLng()

}