package com.victoweng.ciya2.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.data.friend.FriendRequest
import com.victoweng.ciya2.repository.FriendListRepo
import javax.inject.Inject

class FriendsListViewModel @Inject constructor() : ViewModel() {

    val TAG = FriendsListViewModel::class.java.canonicalName

    private val friendListLiveData = MutableLiveData<MutableList<FriendRequest>>()

    fun fetchFriendList() {
        FriendListRepo.fetchFriendsList { onFriendsReceived(it) }
    }

    fun onFriendsReceived(list: MutableList<FriendRequest>) {
        friendListLiveData.value = list
    }

    fun getFriendListLiveData() : LiveData<MutableList<FriendRequest>> {
        return friendListLiveData
    }

    fun onRequestButtonClicked(friendRequest: FriendRequest) {
        //For not just accept
        acceptFriendRequest(friendRequest)
    }

    fun acceptFriendRequest(friendRequest: FriendRequest) {
        if (friendRequest.requestState == FriendRequest.RequestState.NEED_RESPONSE.state) {
            Log.d("CLOWN", "Allowed to accept friend request")
            FriendListRepo.acceptFriendRequest(friendRequest) { result -> onRequestCallback(result, friendRequest)}
        } else {
            Log.e("CLOWN", "Not allowed to accept friend request with state: ${friendRequest.requestState}")
        }
    }

    fun onRequestCallback(isSuccessful: Boolean, friendRequest: FriendRequest) {
        Log.d(TAG, "friend request successful : $isSuccessful")
        friendListLiveData.value?.forEach {
            if (it.requestIds.sendRefId == friendRequest.requestIds.sendRefId) {
                Log.d("CLOWN", "found and will change")
                it.requestState = FriendRequest.RequestState.ACCEPTED.state
            }
        }
        friendListLiveData.value = friendListLiveData.value
    }
}