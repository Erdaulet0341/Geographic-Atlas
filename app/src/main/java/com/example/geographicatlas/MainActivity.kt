package com.example.geographicatlas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.geographicatlas.api.ApiInstance
import com.example.geographicatlas.api.ApiServices
import com.example.geographicatlas.data.Countries
import retrofit2.Call
import retrofit2.Callback
import android.text.Html
import com.example.geographicatlas.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>");

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val api = ApiInstance.getApiInstance().create(ApiServices::class.java)
        val call = api.getDataFromAPI()

        call.enqueue(object : Callback<Countries>{
            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                Log.d("isSuc", response.isSuccessful.toString())
                Log.d("spain", response?.body()?.get(0)?.currencies.toString())
                Log.d("isSuc", response.message().toString())
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                Log.d("errorFail = " , t.message.toString())
            }

        })

    }
}