package com.victoweng.ciya2.repository.database.typeconverter

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import java.sql.Time
import java.util.*

class TimeStampConverter {
    @TypeConverter
    fun fromTimeInMillis(value: Long?) : Timestamp? {
        return value?.let { l -> Timestamp(Date(l)) }
    }

    @TypeConverter
    fun timeStampToMillis(timestamp: Timestamp?) : Long? {
        return timestamp?.let { timestamp -> timestamp.toDate().time }
    }
}