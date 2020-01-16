package com.victoweng.ciya2.repository.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victoweng.ciya2.data.UserProfiles

class UserProfilesConverter {

    @TypeConverter
    fun fromParticipants(userProfiles: UserProfiles?) : String? {
        val gson = Gson()
        val json = gson.toJson(userProfiles)
        return json
    }

    @TypeConverter
    fun fromStringToUserProfiles(json: String?) : UserProfiles? {
        val type = object: TypeToken<UserProfiles> (){}.type
        return Gson().fromJson(json, type)
    }
}