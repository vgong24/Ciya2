package com.victoweng.ciya2.data

import java.io.Serializable

data class  EventDetail (val userCreator: UserProfile = UserProfile(),
                         val categoryType: CategoryType = CategoryType(),
                         val eventLocation: EventLocation = EventLocation(),
                         val title: String = "no title",
                         val description: String = "no description",
                         val startDate: String= "0",
                         val participants: UserProfiles = UserProfiles()) : Serializable {

    override fun toString(): String {
        return "${categoryType.name} title: $title"
    }

}