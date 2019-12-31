package com.victoweng.ciya2.data

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class EventLocation (val lat: Double = 0.0, val lon: Double = 0.0): Parcelable {

    fun toLatLng() : LatLng {

        return LatLng(lat, lon)
    }

    override fun toString(): String {
        return "$lat, $lon"
    }
}