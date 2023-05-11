package com.example.geographicatlas.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {

    companion object {
        val url = "https://restcountries.com/v3.1/"
        fun getApiInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}