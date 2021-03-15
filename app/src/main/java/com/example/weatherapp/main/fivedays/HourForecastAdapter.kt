package com.example.weatherapp.main.fivedays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourForecastItemBinding
import com.example.weatherapp.network.models.HourForecast
import com.squareup.picasso.Picasso

class HourForecastAdapter : RecyclerView.Adapter<HourForecastAdapter.HourForecastViewHolder>() {
    var hourForecastList: List<HourForecast> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourForecastViewHolder =
        DataBindingUtil.inflate<HourForecastItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.hour_forecast_item,
            parent,
            false
        ).let(::HourForecastViewHolder)

    override fun getItemCount(): Int = hourForecastList.size

    override fun onBindViewHolder(holder: HourForecastViewHolder, position: Int) {
        hourForecastList[position].let(holder::bind)
        Picasso.get().load(hourForecastList[position].weatherIconUrl[0].value)
            .into(holder.binding.weatherIcon)
    }

    class HourForecastViewHolder(var binding: HourForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hourForecast: HourForecast) {
            binding.apply {
                vm = hourForecast
                executePendingBindings()
            }
        }
    }
}