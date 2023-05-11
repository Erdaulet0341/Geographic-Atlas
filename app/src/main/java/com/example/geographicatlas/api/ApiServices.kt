package com.example.geographicatlas.api

import com.example.geographicatlas.data.Countries
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("all")
    fun getDataFromAPI(): Call<Countries>

}