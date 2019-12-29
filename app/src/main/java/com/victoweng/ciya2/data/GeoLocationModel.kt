package com.victoweng.ciya2.data

import java.io.Serializable

data class GeoLocationModel(val g : String = "",
                            val l: List<Double> = mutableListOf()) : Serializable {

}