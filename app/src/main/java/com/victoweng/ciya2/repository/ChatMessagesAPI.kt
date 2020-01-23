package com.victoweng.ciya2.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.functions.FirebaseFunctions
import com.victoweng.ciya2.comparator.ChatRoomTimeComparator
import com.victoweng.ciya2.constants.FIRE_CHAT_MESSAGES
import com.victoweng.ciya2.constants.FIRE_CHAT_ROOM
import com.victoweng.ciya2.constants.FIRE_TIME
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.chat.ChatMessage
import com.victoweng.ciya2.data.chat.ChatRoom
import com.victoweng.ciya2.repository.auth.AuthRepo
import javax.inject.Inject

/**
 * Maintains the repo for handling messaging based functionality to and from headend and sync with database
 * Responsible for sending and receiving 1-1 and group messages
 */
class ChatMessagesAPI @Inject constructor(val firestore: FirebaseFirestore, val authRepo: AuthRepo) {

    val TAG = ChatMessagesAPI::class.java.canonicalName

    val functions: FirebaseFunctions by lazy {
        FirebaseFunctions.getInstance()
    }

    val chatRef: CollectionReference by lazy {
        firestore.collection(FIRE_CHAT_ROOM)
    }

    /**
     * Creates a chatroom and initial chat message document as a continuation batch
     * as well as adding the ChatRoom to the current user's AttendingEvents collection
     */
    fun createChatRoom(eventDetail: EventDetail, writeBatch: WriteBatch) {
        val roomRef = chatRef.document(eventDetail.eventId)
        val msgRef = roomRef.collection(FIRE_CHAT_MESSAGES)
        val msgDoc = msgRef.document()
        val userAttendingRef = authRepo.getCurrentUserAttendingEventsCollection()
            .document(eventDetail.eventId)
        val latestMsg = ChatMessage(msgDoc.id, authRepo.createCurrentUserProfile(), "Welcome")
        val chatRoom = ChatRoom(eventDetail.eventId, eventDetail.title, latestMsg)
        eventDetail.chatRoom = chatRoom
        writeBatch.set(roomRef, chatRoom)
        writeBatch.set(msgDoc, latestMsg)
        writeBatch.set(userAttendingRef, chatRoom)
    }


    fun sendMessageToGroup(groupId: String, chatMessage: ChatMessage) {
        val data = hashMapOf(
            "groupId" to groupId,
            "chatMessage" to chatMessage
        )
        functions.getHttpsCallable("sendMessageToGroup")
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
        return getSingleChatId(uid1, authRepo.getCurrentUserId()!!)
    }

    private fun getSingleChatId(uid1: String, uid2: String): String {
        if (uid1.compareTo(uid2) < 0) {
            return uid1 + uid2
        }

        return getSingleChatId(uid2, uid1)
    }

    fun fetchChatRoomsFor(onSuccess: (MutableList<ChatRoom>) -> Unit): Task<QuerySnapshot> {
        return authRepo.getCurrentUserAttendingEventsCollection()
            .get()
            .addOnSuccessListener {
                var list = it.toObjects(ChatRoom::class.java)
                list = list.sortedWith(ChatRoomTimeComparator())
                Log.d(TAG, "fetchChatRooms: Success: ${list.size}")
                onSuccess(list)
            }.addOnFailureListener {
                Log.d(TAG, "fetchChatRooms: Failed ${it.message}")
                onSuccess(mutableListOf())
            }
    }

    fun fetchMessages(channelId: String): Query {
        return chatRef.document(channelId).collection(FIRE_CHAT_MESSAGES).orderBy(FIRE_TIME)
    }
}