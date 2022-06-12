package com.example.velibmapapp

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


data class Station(
    var bikes_available: Int,
    val capacity: Int,
    var ebikes_available: Int,
    val last_reported: Int,
    val lat: Double,
    val lon: Double,
    var name: String,
    var num_docks_available: Int,
    val stationCode: String,
    var station_id: Long,

    ): ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(lat, lon)
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String? {
        return ""
    }
}




