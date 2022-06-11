package com.example.velibmapapp

import com.example.velibmapapp.Station
import retrofit2.http.GET

interface VelibApi {

    @GET("get-all-stations")
    suspend fun getStations() : List<Station>

}
