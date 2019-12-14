package com.victoweng.ciya2.data

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class EventLocation (val lon: Double = 0.0, val lat: Double = 0.0): Serializable {
    override fun toString(): String {
        return "$lat, $lon"
    }
}