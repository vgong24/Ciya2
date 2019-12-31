package com.victoweng.ciya2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class CategoryType(val name: String = "empty", val imageUrl: String = "", val colorRes: Int = 0) : Parcelable