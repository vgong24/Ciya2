package com.victoweng.ciya2.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.victoweng.ciya2.util.date.DateTimeUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDetail(
    var eventId: String = "",
    val userCreator: UserProfile = UserProfile(),
    val categoryType: CategoryType = CategoryType(),
    val eventLocation: EventLocation = EventLocation(),
    val title: String = "no title",
    val description: String = "no description",
    val timestamp: Timestamp = Timestamp.now(),
    val participants: UserProfiles = UserProfiles()
) : Parcelable {

    override fun toString(): String {
        return "${categoryType.name} title: $title"
    }

    fun timeStampFormatted() = DateTimeUtil.asString(timestamp.toDate())

}