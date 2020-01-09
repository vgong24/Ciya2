package com.victoweng.ciya2.ui.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.constants.EVENT_DETAIL
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.EventLocation
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.FireStoreRepo
import com.victoweng.ciya2.repository.FriendListRepo
import com.victoweng.ciya2.ui.custom.JoinButton

class FullEventDetailsViewModel : ViewModel() {

    val TAG = FullEventDetailsViewModel::class.java.canonicalName

    val locationLiveData = MutableLiveData<EventLocation>(EventLocation())
    val eventDetailLiveData = MutableLiveData<EventDetail>()

    fun getEventDetailLiveData(): LiveData<EventDetail> {
        return eventDetailLiveData
    }
    fun getEventDetailsFrom(bundle: Bundle) {
        val detail = bundle.getParcelable(EVENT_DETAIL) as EventDetail
        updateEventDetails(detail)
    }

    fun updateEventDetails(eventDetail: EventDetail?) {
        eventDetailLiveData.value = eventDetail
    }

    fun getEventLocation() = locationLiveData.value!!.toLatLng()

    fun setupJoinButton(join_button: JoinButton, eventDetail: EventDetail) {
        if(isHost(eventDetail, FireAuth.getCurrentUserId()!!)) {
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
        eventDetail.participants.containsUser(FireAuth.getCurrentUserId()!!)

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
        FireStoreRepo.addParticipant(event!!, FireAuth.createCurrentUserProfile()) {
            eventDetailLiveData.value?.participants?.addUser(FireAuth.createCurrentUserProfile())
            eventDetailLiveData.value = eventDetailLiveData.value
        }
    }

    fun leaveEvent() {
        val event = eventDetailLiveData.value
        FireStoreRepo.removeParticipant(event!!.eventId, FireAuth.createCurrentUserProfile()) {
            eventDetailLiveData.value?.participants?.removeUser(FireAuth.createCurrentUserProfile())
            eventDetailLiveData.value = eventDetailLiveData.value
        }
    }

    fun onAddButtonClicked(userProfile: UserProfile) {
        FriendListRepo.sendFriendRequest(userProfile) {
            Log.d("CLOWN", "User ADDEDL $it")
        }
    }
}