package com.example.weatherapp.main.fivedays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FiveDaysForecastLayoutBinding
import com.example.weatherapp.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FiveDaysForecastFragment : Fragment() {

    private val vm: MainViewModel by sharedViewModel()
    private lateinit var binding: FiveDaysForecastLayoutBinding
    private val weatherForecastAdapter =
        WeatherForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FiveDaysForecastLayoutBinding>(
            inflater,
            R.layout.five_days_forecast_layout,
            container,
            false
        ).also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initViewModel()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@FiveDaysForecastFragment
            vm = this@FiveDaysForecastFragment.vm
            forecastRecycler.apply {
                adapter = weatherForecastAdapter.also {
                    it.weatherDetails.observe(viewLifecycleOwner, Observer {
                        this@FiveDaysForecastFragment.vm.selectedWeatherItem.value = it
                        findNavController().navigate(R.id.action_main_fragment_to_hour_fragment)
                    })
                }
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }
    }

    private fun initViewModel() {
        vm.apply {
            daysForecastList.observeForever {
                weatherForecastAdapter.daysForecastList = it
            }
        }
    }
}