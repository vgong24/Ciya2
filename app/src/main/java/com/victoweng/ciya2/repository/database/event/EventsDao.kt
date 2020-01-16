package com.victoweng.ciya2.repository.database.event

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventsDao {

    @Query("SELECT * FROM event_detail_table ORDER BY timeStamp DESC")
    fun getAllEvents(): List<EventDetailEntity>

    @Update
    fun update(eventDetailEntity: EventDetailEntity)

    @Insert
    fun insert(eventDetailEntity: EventDetailEntity)

    @Delete
    fun delete(eventDetailEntity: EventDetailEntity)

    @Query("DELETE FROM event_detail_table")
    fun deleteAllEvents()

}