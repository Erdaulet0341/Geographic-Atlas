package com.example.geographicatlas.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.example.geographicatlas.R
import com.example.geographicatlas.databinding.ActivityMainBinding
import com.example.geographicatlas.fragments.CountriesList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.top_title) + "</font>");

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentList, CountriesList.newInstance()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentList, CountriesList.newInstance()).commit()
            supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.top_title) + "</font>");
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        return true
    }
}