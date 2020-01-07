package com.victoweng.ciya2.data.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.victoweng.ciya2.data.UserProfile
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatMessage(val mesId: String = "",
                       val userProfile: UserProfile = UserProfile(),
                       val message: String = "",
                       val timeStamp: Timestamp = Timestamp.now()) : Parcelable