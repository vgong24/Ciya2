package com.victoweng.ciya2.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.WriteBatch
import com.google.firebase.functions.FirebaseFunctions
import com.victoweng.ciya2.constants.FireAuth
import com.victoweng.ciya2.data.chat.ChatMessage
import com.victoweng.ciya2.data.chat.ChatRoom

/**
 * Maintains the repo for handling messaging based functionality to and from headend and sync with database
 * Responsible for sending and receiving 1-1 and group messages
 */
object ChatMessagesRepo {

    val TAG = ChatMessagesRepo::class.java.canonicalName

    val functions: FirebaseFunctions by lazy {
        FirebaseFunctions.getInstance()
    }

    val chatRef: CollectionReference by lazy {
        FireStoreRepo.fireStore.collection("chatRooms")
    }

    //Quick approach to append writing more to headend by passing through the writeBatch from creating
    //the eventDetails
    fun createChatRoom(groupId: String, title: String, writeBatch: WriteBatch) {
        var roomRef = chatRef.document(groupId)
        var messageRef = roomRef.collection("chatMessages")
        var messageDoc = messageRef.document()
        var userEventsRef = FireStoreRepo.fireStore.collection("users")
            .document(FireAuth.getCurrentUserId()!!)
            .collection("eventsAttending")
            .document(groupId)

        var latestMessage = ChatMessage(messageDoc.id, FireAuth.createCurrentUserProfile(), "Welcome")
        val chatroom = ChatRoom(roomId = groupId, title = title, latestMessage = latestMessage)
        writeBatch.set(roomRef, chatroom)
        writeBatch.set(messageDoc, latestMessage)
        writeBatch.set(userEventsRef, chatroom)
    }


    fun sendMessageToGroup(groupId: String, chatMessage: ChatMessage) {
        val data = hashMapOf(
            "groupId" to groupId,
            "chatMessage" to chatMessage
        )
           functions.getHttpsCallable("sendMessageToGroup")
            .call(data)
        //TODO implement storing to local db
    }

    fun sendMessageToSingle(chatId: String, chatMessage: ChatMessage) {

        //TODO implement storing to local db
    }

    fun fetchGroupMessagesAt(groupId: String, startMessageId: String, limit: Int) {

    }

    fun fetchSingleMessagesAt(chatId: String, startMessageId: String, limit: Int) {

    }

    fun getSingleChatId(uid1: String): String {
        return getSingleChatId(uid1, FireAuth.getCurrentUserId()!!)
    }

    private fun getSingleChatId(uid1: String, uid2: String): String {
        if (uid1.compareTo(uid2) < 0) {
            return uid1 + uid2
        }

        return getSingleChatId(uid2, uid1)
    }

    fun fetchChatRoomsFor(onSuccess: (MutableList<ChatRoom>) -> Unit) : Task<QuerySnapshot> {
        return FireStoreRepo.getCurrentUserRef().collection("eventsAttending")
            .get()
            .addOnSuccessListener {
                var list = it.toObjects(ChatRoom::class.java)
                Log.d("CLOWN", "Success: ${list.size}")
                onSuccess(list)
            }.addOnFailureListener {
                Log.d("CLOWN", "Failed ${it.message}")
                onSuccess(mutableListOf())
            }
    }
}