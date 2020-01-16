package com.victoweng.ciya2.repository.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victoweng.ciya2.data.EventLocation

class EventLocationConverter {

    @TypeConverter
    fun fromEventLocationTo(eventLocation: EventLocation?): String? {
        val gson = Gson()
        val json = gson.toJson(eventLocation)
        return json
    }

    @TypeConverter
    fun fromStringToEventLocation(json: String?): EventLocation? {
        val type = object: TypeToken<EventLocation>(){}.type
        return Gson().fromJson(json, type)
    }
}