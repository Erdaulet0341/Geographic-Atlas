package com.example.geographicatlas.api

import com.example.geographicatlas.data.Countries
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("all")
    fun getAllCountries(): Call<Countries>

    @GET("alpha/{cca2}")
    fun getCountryByCode(@Path("cca2") cca2:String): Call<Countries>

}