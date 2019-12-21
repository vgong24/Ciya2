package com.victoweng.ciya2.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class  EventDetail (val userCreator: UserProfile = UserProfile(),
                         val categoryType: CategoryType = CategoryType(),
                         val eventLocation: EventLocation = EventLocation(),
                         val title: String = "no title",
                         val description: String = "no description",
                         val timestamp: Timestamp = Timestamp.now(),
                         val participants: UserProfiles = UserProfiles()) : Serializable {

    override fun toString(): String {
        return "${categoryType.name} title: $title"
    }

}