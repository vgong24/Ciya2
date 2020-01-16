package com.victoweng.ciya2.repository.database.event

import com.victoweng.ciya2.data.EventDetail

class EventDetailEntityMapper {
    fun map(eventDetail: EventDetail): EventDetailEntity {
        return EventDetailEntity(
            eventDetail.eventId,
            eventDetail.userCreator.uid,
            eventDetail.categoryType.name,
            eventDetail.eventLocation,
            eventDetail.title,
            eventDetail.description,
            eventDetail.timestamp,
            eventDetail.participants
        )
    }
}