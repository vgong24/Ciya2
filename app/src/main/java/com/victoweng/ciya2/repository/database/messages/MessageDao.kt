package com.victoweng.ciya2.repository.database.messages

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages_table")
    fun getAllMessages(): LiveData<List<MessageEntity>>

    @Query("SELECT * FROM messages_table WHERE eventId LIKE :roomId ORDER BY timestamp DESC")
    fun getMessagesFrom(roomId: String) : LiveData<List<MessageEntity>>

    @Update
    fun update(messageEntity: MessageEntity)

    @Insert
    fun insert(messageEntity: MessageEntity)

    @Delete
    fun delete(messageEntity: MessageEntity)

    @Query("DELETE FROM messages_table")
    fun deleteAllMessages()

}