package com.victoweng.ciya2.repository.database

import android.content.Context
import androidx.room.*
import com.victoweng.ciya2.repository.database.messages.MessageDao
import com.victoweng.ciya2.repository.database.messages.MessageEntity
import com.victoweng.ciya2.repository.database.typeconverter.TimeStampConverter

@Database(entities = arrayOf(MessageEntity::class), version = 1)
@TypeConverters(TimeStampConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        val DB_NAME = "ciya_db"
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, DB_NAME)
            .build()

    }
}