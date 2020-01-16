package com.victoweng.ciya2.repository.event

import android.util.Log
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.repository.auth.AuthRepo
import com.victoweng.ciya2.repository.database.event.EventDetailEntityMapper
import com.victoweng.ciya2.repository.database.event.EventsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsRepo @Inject constructor(val eventsDao: EventsDao, val eventApi: EventApi, val authRepo: AuthRepo) {

    fun createEvent(eventDetail: EventDetail, callback: (EventDetail) -> Unit) {
        val eventDetailRef = eventApi.createEventDetailReference()
        val eventId = eventDetailRef.id
        eventDetail.eventId = eventId


        //save on headend then respond into local database
        eventApi.createEvent(eventDetail, eventDetailRef) { callback(it) }
    }

    /**
     * Stores eventDetail to Room database. HOWEVER. At this point I've just learned that I could just
     * use Firestore since it saves it locally. But having this is nice for future reference on ROOM
     * experience.
     */
    private fun saveEventDetailToDB(eventDetail: EventDetail, callback: (EventDetail) -> Unit) {

        val job = Job()
        CoroutineScope(IO + job).launch {
            val entity = EventDetailEntityMapper().map(eventDetail)
            eventsDao.insert(entity)

            val list = eventsDao.getAllEvents()
            Log.d("CLOWN", "save into list size : ${list?.size}")
            callback(eventDetail)
            job.complete()
        }

    }

    fun deleteAllEvents() {
        eventsDao.deleteAllEvents()
    }

}