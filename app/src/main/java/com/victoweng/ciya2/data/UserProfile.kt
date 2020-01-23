package com.victoweng.ciya2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile (val uid: String = "",
                        val email: String = "",
                        val userName: String = "",
                        val registrationTokens: MutableList<String> = mutableListOf()) : Parcelable