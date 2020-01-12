package com.victoweng.ciya2.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.victoweng.ciya2.repository.database.messages.MessageDao
import com.victoweng.ciya2.repository.database.messages.MessageEntity
import com.victoweng.ciya2.repository.database.typeconverter.TimeStampConverter

@Database(entities = arrayOf(MessageEntity::class), version = 1)
@TypeConverters(TimeStampConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

    }
}