package com.victoweng.ciya2.repository.database.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.victoweng.ciya2.data.EventLocation
import com.victoweng.ciya2.data.UserProfiles

@Entity(tableName = "event_detail_table")
data class EventDetailEntity(
    @PrimaryKey val eventId: String,
    val creatorUid: String,
    val categoryName: String,
    val eventLocation: EventLocation,
    val title: String,
    val description: String,
    val timeStamp: Timestamp,
    val participants: UserProfiles
)
