package com.victoweng.ciya2.data

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class EventLocation (val lat: Double = 0.0, val lon: Double = 0.0): Serializable {

    fun toLatLng() : LatLng {

        return LatLng(lat, lon)
    }

    override fun toString(): String {
        return "$lat, $lon"
    }
}