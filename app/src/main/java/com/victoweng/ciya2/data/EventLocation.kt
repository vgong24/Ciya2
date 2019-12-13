package com.victoweng.ciya2.data

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class EventLocation (val lon: Double?, val lat: Double?): Serializable {
    override fun toString(): String {
        return "$lat, $lon"
    }
}