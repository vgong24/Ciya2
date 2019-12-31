package com.victoweng.ciya2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class UserProfiles (val userList: MutableList<UserProfile> = mutableListOf()) : Parcelable {
    fun addUser(userProfile: UserProfile) {
        userList.add(userProfile)
    }
}