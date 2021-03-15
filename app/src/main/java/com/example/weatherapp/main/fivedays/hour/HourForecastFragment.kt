package com.example.weatherapp.main.fivedays.hour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourForecastLayoutBinding
import com.example.weatherapp.main.MainViewModel
import com.example.weatherapp.main.fivedays.HourForecastAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HourForecastFragment : Fragment() {

    private val vm : MainViewModel by sharedViewModel()
    private lateinit var binding : HourForecastLayoutBinding
    private val adapter = HourForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<HourForecastLayoutBinding>(
            inflater,
            R.layout.hour_forecast_layout,
            container,
            false
        ).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@HourForecastFragment
            vm = this@HourForecastFragment.vm
            hourRecycler.adapter = this@HourForecastFragment.adapter.apply{
                hourForecastList = this@HourForecastFragment.vm.selectedWeatherItem.value?.hourly ?: emptyList()
            }
        }
    }
}