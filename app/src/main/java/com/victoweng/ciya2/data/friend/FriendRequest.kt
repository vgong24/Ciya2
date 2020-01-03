package com.victoweng.ciya2.data.friend

import android.os.Parcelable
import com.victoweng.ciya2.data.UserProfile
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendRequest(
    val requestIds: FRequestIds = FRequestIds(),
    val sender: UserProfile = UserProfile(),
    val receiver: UserProfile = UserProfile(),
    var requestState: String = ""
) : Parcelable {

    enum class RequestState(val state: String) {
        REQUEST_SENT("Request_Sent"),
        NEED_RESPONSE("Need_Response"),
        REJECT("Reject"),
        BLOCKED("Blocked"),
        ACCEPTED("Accepted")
    }

}