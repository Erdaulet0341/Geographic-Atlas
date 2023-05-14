package com.example.geographicatlas.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.geographicatlas.R
import com.example.geographicatlas.api.ApiInstance
import com.example.geographicatlas.api.ApiServices
import com.example.geographicatlas.data.mainData.Countries
import com.example.geographicatlas.data.mainData.CountriesList
import com.example.geographicatlas.databinding.FragmentCountryDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.ceil

class CountryDetails : Fragment() {

    lateinit var binding: FragmentCountryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryDetailsBinding.inflate(inflater)
        binding.progressBar2.visibility = View.VISIBLE
        val args = arguments
        val cca2 = args?.getString("cca2")!!
//        Log.d("cca2" , cca2)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            setDisplayHomeAsUpEnabled(true)
        }

//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        createPage(cca2)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    fragmentManager?.beginTransaction()?.replace(R.id.fragmentList, CountriesList.newInstance())?.commit()
                    (activity as AppCompatActivity).supportActionBar?.title  = Html.fromHtml("<font color=\"black\">" + getString(R.string.top_title) + "</font>");
                    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        )
        return binding.root
    }

    private fun createPage(cca2: String) {
        val api = ApiInstance.getApiInstance().create(ApiServices::class.java)
        val call = api.getCountryByCode(cca2)

        call.enqueue(object : Callback<Countries>{
            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val country = response.body()?.get(0)!!

                //for toolbar title
                (activity as AppCompatActivity).supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + country.name.common + "</font>");


                //for population
                if(country.population >= 1000000){
                    val population  = country.population.toDouble()/1000000
                    val populationRounded = ceil(population)
                    binding.populationDet.text = "${populationRounded.toInt()} mln"
                }
                else if(country.population in 1001..999999){
                    val last = country.population % 1000
                    val first = country.population / 1000
                    binding.populationDet.text = "$first $last"
                }
                else{
                    binding.populationDet.text = country.population.toString()
                }


                //for area
                if(country.area >= 1000000){
                    val first = country.area.toInt() / 1000000
                    val midd = country.area.toInt() % 1000000
                    val middle = midd / 1000
                    val last = midd % 1000

                    binding.areaDet.text = "$first $middle $last km\u00B2"
                }
                else if(country.area<1000000 && country.area>=1000){
                    val last = country.area.toInt() % 1000
                    val first = country.area.toInt() / 1000
                    if(last.toString().length==2) binding.areaDet.text = "$first 0$last km\u00B2"
                    else if(last.toString().length==1) binding.areaDet.text = "$first 00$last km\u00B2"
                    else binding.areaDet.text = "$first $last km\u00B2"
                }
                else{
                    binding.areaDet.text = "${country.area.toInt()} km\u00B2"
                }

                //for subregion
                binding.regionDet.text = country.subregion
                if(country.subregion?.toString() == null){
                    binding.regionDet.text = "None region"
                }

                //for capital
                binding.capitalDet.text = country.capital?.get(0)
                if(country.capital?.get(0) == null){
                    binding.capitalDet.text =  "None capital"
                }

                //for capital coordinates
                if(country.capitalInfo?.latlng?.get(0)  != null){
                    val firstDegree = country.capitalInfo.latlng[0].toString().split(".")
                    val secondDegree = country.capitalInfo.latlng[1].toString().split(".")
                    binding.CapitalCoorDet.text = "${firstDegree[0]}\u00B0${firstDegree[1]}', ${secondDegree[0]}\u00B0${secondDegree[1]}'"
                }
                else{
                    binding.CapitalCoorDet.text = "None capital Coordinates"
                }

                //for timezones
                var textForTimezones = ""
                country.timezones.forEach {
                    var text = "GMT" + it[3]
                    if(it[4] == '0') text += it[5]
                    else text = text  + it[4] + it[5]
                    textForTimezones += "$text\n"
                }
                binding.timezoneDet.text = textForTimezones

                //for image
                val img = binding.imgDet
                val url = country.flags.png
                Glide.with(img)
                    .load(url)
                    .placeholder(R.drawable.ic_baseline_access_time_24)
                    .error(R.drawable.ic_baseline_hide_image_24)
                    .fallback(R.drawable.ic_baseline_access_time_24)
                    .into(img)

                //for progress bar
                binding.progressBar2.visibility = View.INVISIBLE

            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                Log.d("errorFailure", t.message.toString())
                Toast.makeText(activity, "Check internet connection and try again!", Toast.LENGTH_SHORT).show()
                binding.progressBar2.visibility = View.INVISIBLE
            }

        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = CountryDetails()
    }
}