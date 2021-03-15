package com.example.weatherapp.main.fivedays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.SingleLiveEvent
import com.example.weatherapp.databinding.DayForecastLayoutBinding
import com.example.weatherapp.network.models.WeatherDayItem
import com.squareup.picasso.Picasso

class WeatherForecastAdapter :
    RecyclerView.Adapter<WeatherForecastAdapter.DayForecastViewHolder>(), OnExpandListener,
    OnDetailsListener {
    var daysForecastList: List<WeatherDayItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val weatherDetails = SingleLiveEvent<WeatherDayItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayForecastViewHolder =
        DataBindingUtil.inflate<DayForecastLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.day_forecast_layout,
            parent,
            false
        ).let(WeatherForecastAdapter::DayForecastViewHolder)

    override fun getItemCount(): Int = daysForecastList.size

    override fun onBindViewHolder(holder: DayForecastViewHolder, position: Int) {
        val item = daysForecastList[position]
        Picasso.get().load(item.hourly[0].weatherIconUrl[0].value)
            .into(holder.binding.weatherIcon)
        item.position = position
        holder.bind(item, this, this)
    }

    class DayForecastViewHolder(var binding: DayForecastLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            weatherDayItem: WeatherDayItem,
            listener: OnExpandListener,
            detailsListener: OnDetailsListener
        ) {
            binding.apply {
                vm = weatherDayItem
                this.listener = listener
                buttonListener = detailsListener
                executePendingBindings()
            }
        }
    }

    override fun expand(weatherDayItem: WeatherDayItem) {
        val expanded = weatherDayItem.expanded
        weatherDayItem.expanded = !expanded
        notifyItemChanged(weatherDayItem.position)
    }

    override fun getDetails(weatherDayItem: WeatherDayItem) {
        weatherDetails.value = weatherDayItem
    }
}

interface OnExpandListener {
    fun expand(weatherDayItem: WeatherDayItem)
}

interface OnDetailsListener {
    fun getDetails(weatherDayItem: WeatherDayItem)
}
