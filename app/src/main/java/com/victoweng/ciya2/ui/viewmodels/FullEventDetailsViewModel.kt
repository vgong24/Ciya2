package com.victoweng.ciya2.ui.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.constants.EVENT_DETAIL
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.EventLocation
import com.victoweng.ciya2.repository.FireStoreRepo
import com.victoweng.ciya2.ui.custom.JoinButton

class FullEventDetailsViewModel : ViewModel() {

    val TAG = FullEventDetailsViewModel::class.java.canonicalName

    val locationLiveData = MutableLiveData<EventLocation>(EventLocation())
    val eventDetailLiveData = MutableLiveData<EventDetail>()

    fun getEventDetailsFrom(bundle: Bundle) {
        val detail = bundle.getParcelable(EVENT_DETAIL) as EventDetail
        updateEventDetails(detail)
    }

    fun updateEventDetails(eventDetail: EventDetail?) {
        eventDetailLiveData.value = eventDetail
    }

    fun getEventLocation() = locationLiveData.value!!.toLatLng()

    fun setupJoinButton(join_button: JoinButton, eventDetail: EventDetail) {
        if(isHost(eventDetail, FireRepo.getCurrentUserId()!!)) {
            join_button.setRequestState(JoinButton.RequestState.HOST)
        } else {
            join_button.setRequestState(if(isUserCurrentlyAttendingEvent(eventDetail)) JoinButton.RequestState.LEAVE else JoinButton.RequestState.JOIN)
        }
        join_button.setOnClickListener {
            Log.d(TAG, "join event")
            onJoinButtonClicked(join_button)
        }
    }

    private fun isUserCurrentlyAttendingEvent(eventDetail: EventDetail) =
        eventDetail.participants.containsUser(FireRepo.getCurrentUserId()!!)

    fun isHost(eventDetail: EventDetail, userId: String) :Boolean {
        return eventDetail.userCreator.uid == userId
    }

    fun onJoinButtonClicked(joinButton: JoinButton) {
        when(joinButton.getCurrentState()) {
            JoinButton.RequestState.JOIN -> joinEvent()
            JoinButton.RequestState.JOIN_REQUESTED -> leaveEvent()
            JoinButton.RequestState.LEAVE -> leaveEvent()
        }

    }

    fun joinEvent() {
        val event = eventDetailLiveData.value
        FireStoreRepo.addParticipant(event!!.eventId, FireRepo.createCurrentUserProfile())
            .addOnSuccessListener {
                eventDetailLiveData.value?.participants?.addUser(FireRepo.createCurrentUserProfile())
                eventDetailLiveData.value = eventDetailLiveData.value
                Log.d(TAG, "userAdded...")
            }.addOnFailureListener {
                Log.d(TAG, "exception " + it.message)
            }

    }

    fun leaveEvent() {
        val event = eventDetailLiveData.value
        FireStoreRepo.removeParticipant(event!!.eventId, FireRepo.createCurrentUserProfile())
            .addOnSuccessListener {
                eventDetailLiveData.value?.participants?.removeUser(FireRepo.createCurrentUserProfile())
                eventDetailLiveData.value = eventDetailLiveData.value
                Log.d(TAG, "user removed...")
            }.addOnFailureListener {
                Log.d(TAG, "failed to remove " + it.message)
            }

    }
}