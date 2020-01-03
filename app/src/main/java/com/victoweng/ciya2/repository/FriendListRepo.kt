package com.victoweng.ciya2.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.victoweng.ciya2.constants.FIRE_FRIEND_LIST
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.friend.FriendRequest
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.data.friend.FRequestIds

object FriendListRepo {

    val TAG = FriendListRepo::class.java.canonicalName

    fun sendFriendRequest(friendProfile: UserProfile, resultCallback: (Boolean) -> Unit) {
        var friendlistRef = FireStoreRepo.fireStore.collection(FIRE_FRIEND_LIST)
        friendlistRef.whereEqualTo("sender.uid", FireRepo.getCurrentUserId()!!)
            .whereEqualTo("receiver.uid", friendProfile.uid)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    postSendFriendRequest(friendlistRef, friendProfile, resultCallback)
                } else {
                    Log.e(TAG, "friend request ids exist")
                    resultCallback(false)
                }
            }
    }

    private fun postSendFriendRequest(
        friendlistRef: CollectionReference,
        friendProfile: UserProfile,
        resultCallback: (Boolean) -> Unit
    ) {
        var userRef = friendlistRef.document()
        var friendRef = friendlistRef.document()
        var currentUser = FireRepo.createCurrentUserProfile()
        var requestIds = FRequestIds(userRef.id, friendRef.id)

        FireStoreRepo.fireStore.runBatch { writeBatch ->
            var sendRequest = FriendRequest(
                requestIds,
                currentUser,
                friendProfile,
                FriendRequest.RequestState.REQUEST_SENT.state
            )
            var receiveRequest = FriendRequest(
                requestIds,
                friendProfile,
                currentUser,
                FriendRequest.RequestState.NEED_RESPONSE.state
            )
            writeBatch.set(userRef, sendRequest)
            writeBatch.set(friendRef, receiveRequest)
        }.addOnSuccessListener {
            resultCallback(true)
        }.addOnFailureListener {
            Log.e(TAG, "failed to send Friend request: ${it.message}")
            resultCallback(false)
        }
    }

    fun acceptFriendRequest(friendRequest: FriendRequest, resultCallback: (Boolean) -> Unit) {
        Log.d("CLOWN", "AcceptFriendRequest")
        var friendlistRef = FireStoreRepo.fireStore.collection(FIRE_FRIEND_LIST)
        var sendRef = friendlistRef.document(friendRequest.requestIds.sendRefId)
        var receiveRef = friendlistRef.document(friendRequest.requestIds.receiveRefId)
        FireStoreRepo.fireStore.runBatch {
            writeBatch ->
            writeBatch.update(sendRef, "requestState", FriendRequest.RequestState.ACCEPTED.state)
            writeBatch.update(receiveRef, "requestState", FriendRequest.RequestState.ACCEPTED.state)
        }.addOnSuccessListener {
            Log.d(TAG, "Successfully Accepted Friend Request")
            resultCallback(true)
        }.addOnFailureListener {
            Log.e(TAG, "Failed to Accept Friend Request ${it.message}")
            resultCallback(false)
        }
    }

    fun denyFriendRequest(uid: String) {

    }

    fun removeFriend(uid: String) {

    }

    fun fetchFriendsList(listCallback:(MutableList<FriendRequest>) -> Unit) {
        val ref = FireStoreRepo.fireStore.collection(FIRE_FRIEND_LIST)
        ref.whereEqualTo("sender.uid", FireRepo.getCurrentUserId()!!)
            .get()
            .addOnSuccessListener {
                var friendList = it.toObjects(FriendRequest::class.java)
                listCallback(friendList)
            }.addOnFailureListener {
                Log.e(TAG, "Failed to fetch friends ${it.message}")
                listCallback(mutableListOf())
            }
    }

}