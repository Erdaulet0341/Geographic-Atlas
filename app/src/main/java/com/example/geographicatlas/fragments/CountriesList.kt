package com.example.geographicatlas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geographicatlas.R
import com.example.geographicatlas.api.ApiInstance
import com.example.geographicatlas.api.ApiServices
import com.example.geographicatlas.data.Countries
import com.example.geographicatlas.databinding.FragmentCountriesListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesList : Fragment() {

    lateinit var binding: FragmentCountriesListBinding
    lateinit var recyclerViewAdapter: CountriesListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesListBinding.inflate(inflater)


        setupRecyclerView()
        createList()

        return binding.root
    }

    private fun createList() {
        val api = ApiInstance.getApiInstance().create(ApiServices::class.java)
        val call = api.getDataFromAPI()

        call.enqueue(object: Callback<Countries>{
            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val countries = response.body()!!
                Log.d("size=" , countries.size.toString())
                recyclerViewAdapter.setList(countries)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                Log.d("errorFail = " , t.message.toString())
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupRecyclerView() {
        binding.recyclerList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewAdapter = CountriesListAdapter()
            adapter = recyclerViewAdapter
        }    }

    companion object {
        @JvmStatic
        fun newInstance() = CountriesList()
    }
}