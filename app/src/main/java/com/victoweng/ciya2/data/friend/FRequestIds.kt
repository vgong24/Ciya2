package com.victoweng.ciya2.data.friend

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FRequestIds(
    val sendRefId: String = "",
    val receiveRefId: String = ""
) : Parcelable