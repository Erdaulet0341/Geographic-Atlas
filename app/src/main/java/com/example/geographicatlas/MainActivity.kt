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
import com.example.geographicatlas.fragments.CountriesList
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.top_title) + "</font>");

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentList, CountriesList.newInstance()).commit()
    }
}