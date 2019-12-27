package com.victoweng.ciya2.data

import java.io.Serializable

data class UserProfiles (val userList: MutableList<UserProfile> = mutableListOf()) : Serializable {
    fun addUser(userProfile: UserProfile) {
        userList.add(userProfile)
    }
}