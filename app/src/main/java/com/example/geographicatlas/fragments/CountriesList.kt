package com.example.geographicatlas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geographicatlas.adapter.CountriesListAdapter
import com.example.geographicatlas.api.ApiInstance
import com.example.geographicatlas.api.ApiServices
import com.example.geographicatlas.data.Countries
import com.example.geographicatlas.data.Country
import com.example.geographicatlas.databinding.FragmentCountriesListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesList : Fragment() {

    lateinit var binding: FragmentCountriesListBinding
    lateinit var recyclerViewAdapterForAsia: CountriesListAdapter
    lateinit var recyclerViewAdapterForEurope: CountriesListAdapter
    lateinit var recyclerViewAdapterForOceania: CountriesListAdapter
    lateinit var recyclerViewAdapterForAfrica: CountriesListAdapter
    lateinit var recyclerViewAdapterForNorthAmerica: CountriesListAdapter
    lateinit var recyclerViewAdapterForSouthAmerica: CountriesListAdapter
    lateinit var recyclerViewAdapterForAntarctica: CountriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesListBinding.inflate(inflater)
        binding.progressBar.visibility = View.VISIBLE
        setupRecyclerView()
        createList()

        return binding.root
    }

    private fun createList() {
        val api = ApiInstance.getApiInstance().create(ApiServices::class.java)
        val call = api.getAllCountries()

        call.enqueue(object: Callback<Countries>{
            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val countries = response.body()!!
//                Log.d("size=" , countries.size.toString())
                countries.forEach{ i -> i.unMember = true }


                val countriesAsia = ArrayList<Country>()
                val countriesEurope = ArrayList<Country>()
                val countriesAfrica = ArrayList<Country>()
                val countriesOceania = ArrayList<Country>()
                val countriesAntarctica = ArrayList<Country>()
                val countriesNorthAmerica = ArrayList<Country>()
                val countriesSouthAmerica = ArrayList<Country>()


//                Log.d("size=" , countries.size.toString())

//                var setContinents = mutableSetOf<String>()
//                countries.forEach {
//                    setContinents.add(it.continents[0])
//                }
//                Log.d("set", setContinents.toString())


                countries.forEach {
                    if(it.continents.contains("Asia")) countriesAsia.add(it)
                    if(it.continents.contains("Europe")) countriesEurope.add(it)
                    if(it.continents.contains("Oceania")) countriesOceania.add(it)
                    if(it.continents.contains("Africa")) countriesAfrica.add(it)
                    if(it.continents.contains("North America")) countriesNorthAmerica.add(it)
                    if(it.continents.contains("South America")) countriesSouthAmerica.add(it)
                    if(it.continents.contains("Antarctica")) countriesAntarctica .add(it)
                }

                recyclerViewAdapterForAsia.setList(countriesAsia)
                recyclerViewAdapterForAsia.notifyDataSetChanged()

                recyclerViewAdapterForEurope.setList(countriesEurope)
                recyclerViewAdapterForEurope.notifyDataSetChanged()

                recyclerViewAdapterForOceania.setList(countriesOceania)
                recyclerViewAdapterForOceania.notifyDataSetChanged()

                recyclerViewAdapterForAfrica.setList(countriesAfrica)
                recyclerViewAdapterForAfrica.notifyDataSetChanged()

                recyclerViewAdapterForSouthAmerica.setList(countriesSouthAmerica)
                recyclerViewAdapterForSouthAmerica.notifyDataSetChanged()

                recyclerViewAdapterForNorthAmerica.setList(countriesNorthAmerica)
                recyclerViewAdapterForNorthAmerica.notifyDataSetChanged()

                recyclerViewAdapterForAntarctica.setList(countriesAntarctica)
                recyclerViewAdapterForAntarctica.notifyDataSetChanged()
                binding.progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                Log.d("errorFailure" , t.message.toString())
                Toast.makeText(activity, "Check internet connection and try again!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.INVISIBLE
            }

        })
    }

    private fun setupRecyclerView() {
        binding.recyclerListAsia.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForAsia = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForAsia }

        binding.recyclerListEurope.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForEurope = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForEurope }

        binding.recyclerListOceania.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForOceania = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForOceania }

        binding.recyclerListAfrica.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForAfrica = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForAfrica }

        binding.recyclerListNorthAmerica.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForNorthAmerica = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForNorthAmerica }

        binding.recyclerListSouthAmerica.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForSouthAmerica = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForSouthAmerica }

        binding.recyclerListAntarctica.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapterForAntarctica = CountriesListAdapter(requireContext(), requireFragmentManager())
            adapter = recyclerViewAdapterForAntarctica }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CountriesList()
    }
}