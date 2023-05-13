package com.example.geographicatlas.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.example.geographicatlas.R
import com.example.geographicatlas.data.Country
import com.example.geographicatlas.databinding.CountriesListItemBinding
import java.math.RoundingMode
import kotlin.math.ceil

class CountriesListAdapter(private val context: Context): RecyclerView.Adapter<CountriesListAdapter.CountryHolder>() {

    var newsList = ArrayList<Country>()

    fun setList(arr: ArrayList<Country>){
        this.newsList = arr

    }

    class CountryHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = CountriesListItemBinding.bind(item)
        val expandableLayout = binding.expandableLayout
        val collapsed = binding.collapsed
        val constrLayoutTop = binding.constrLayoutTop

        fun bind(country: Country){
            binding.nameOfCountry.text = country.name.common

            if(country.area >= 1000000){
                val area = country.area / 1000000
                val roundedArea = area.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
                binding.areaList.text = "$roundedArea mln km\u00B2"
            }
            else if(country.area<1000000 && country.area>=1000){
                val last = country.area.toInt() % 1000
                val first = country.area.toInt() / 1000
                if(last.toString().length==2) binding.areaList.text = "$first 0$last km\u00B2"
                else if(last.toString().length==1) binding.areaList.text = "$first 00$last km\u00B2"
                else binding.areaList.text = "$first $last km\u00B2"
            }
            else{
                binding.areaList.text = "${country.area.toInt()} km\u00B2"
            }

            if(country.population >= 1000000){
                val population  = country.population.toDouble()/1000000
                val populationRounded = ceil(population)
                binding.populationList.text = "${populationRounded.toInt()} mln"
            }
            else{
                val last = country.population % 1000
                val first = country.population / 1000
                binding.populationList.text = "$first $last"
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
        val country = newsList[position]
        holder.bind(country)


        if(country.unMember){
            holder.expandableLayout.visibility = View.GONE
            holder.collapsed.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_expand_more_24))
        }
        else{
            holder.expandableLayout.visibility = View.VISIBLE
            holder.collapsed.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_expand_less_24))
        }

        holder.constrLayoutTop.setOnClickListener {
            country.unMember = !country.unMember
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}