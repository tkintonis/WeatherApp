package com.example.weatherapp.main.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CurrentForeacastLayoutBinding
import com.example.weatherapp.main.MainViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CurrentForecastFragment : Fragment() {

    private val vm: MainViewModel by sharedViewModel()
    private lateinit var binding: CurrentForeacastLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<CurrentForeacastLayoutBinding>(
            inflater,
            R.layout.current_foreacast_layout,
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
            lifecycleOwner = this@CurrentForecastFragment
            vm = this@CurrentForecastFragment.vm
        }
    }

    private fun initViewModel() {
        vm.apply {
            currentWeatherForecast.observeForever {
                if (!it.weatherIconUrl.isNullOrEmpty())
                    Picasso.get().load(it.weatherIconUrl[0].value)
                        .into(binding.weatherIcon)
            }
        }
    }

}