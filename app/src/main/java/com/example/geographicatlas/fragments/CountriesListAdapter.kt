package com.example.geographicatlas.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geographicatlas.R
import com.example.geographicatlas.data.Country
import com.example.geographicatlas.databinding.CountriesListItemBinding

class CountriesListAdapter: RecyclerView.Adapter<CountriesListAdapter.CountryHolder>() {

    var newsList = ArrayList<Country>()

    fun setList(arr: ArrayList<Country>){
        this.newsList = arr

    }

    class CountryHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = CountriesListItemBinding.bind(item)

        fun bind(country: Country){
            binding.nameOfCountry.text = country.name.common

            if(country.capital?.size !=0){
                binding.capital.text = country.capital?.get(0)!!
            }
            else{
                binding.capital.text = "No Capital"
            }

            val img = binding.imgList

            val url = country.flags.png
            Glide.with(img)
                .load(url)
                .placeholder(R.drawable.ic_baseline_hide_image_24)
                .error(R.drawable.ic_baseline_hide_image_24)
                .fallback(R.drawable.ic_baseline_hide_image_24)
                .into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_list_item, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}