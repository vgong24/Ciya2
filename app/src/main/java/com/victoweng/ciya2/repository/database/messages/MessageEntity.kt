package com.victoweng.ciya2.repository.database.messages

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.util.*

@Entity(tableName = "messages_table")
data class MessageEntity(@PrimaryKey val messageId: Int,
                         val eventId: String,
                         val messengerId: String,
                         val text: String,
                         val timestamp: Timestamp?)