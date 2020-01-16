package com.victoweng.ciya2.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victoweng.ciya2.repository.database.event.EventDetailEntity
import com.victoweng.ciya2.repository.database.event.EventsDao
import com.victoweng.ciya2.repository.database.messages.MessageDao
import com.victoweng.ciya2.repository.database.messages.MessageEntity
import com.victoweng.ciya2.repository.database.typeconverter.EventLocationConverter
import com.victoweng.ciya2.repository.database.typeconverter.TimeStampConverter
import com.victoweng.ciya2.repository.database.typeconverter.UserProfilesConverter

/**
 * Realized later that I can just use FirebaseFireStore and database to handle all my
 * local database needs and will not require using Room.
 * However, the experience is invaluable and is a good reference for job references
 *
 */
@Database(
    entities = arrayOf(
        MessageEntity::class,
        EventDetailEntity::class
    ), version = 1
)
@TypeConverters(
    value = arrayOf(
        TimeStampConverter::class, EventLocationConverter::class,
        UserProfilesConverter::class
    )
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    abstract fun eventsDao(): EventsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        val DB_NAME = "ciya_db"
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DB_NAME
        )
            .build()

    }
}