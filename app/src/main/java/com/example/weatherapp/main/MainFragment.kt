package com.example.weatherapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapp.*
import com.example.weatherapp.common.StateAdapter
import com.example.weatherapp.common.hideKeyboard
import com.example.weatherapp.common.showErrorMessage
import com.example.weatherapp.databinding.MainFragmentLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.mcxiaoke.koi.ext.toast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private val vm: MainViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentLayoutBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<MainFragmentLayoutBinding>(
            inflater,
            R.layout.main_fragment_layout,
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
            lifecycleOwner = this@MainFragment
            vm = this@MainFragment.vm

            viewPager.adapter = StateAdapter(this@MainFragment)
            TabLayoutMediator(tabLayoutGroupHost, viewPager) { tab, position ->
                tab.text = getTitlePosition(position)
            }.attach()
        }
    }

    private fun getTitlePosition(position: Int): String? {
        when (position) {
            0 -> return getString(R.string.current)
            1 -> return getString(R.string.five_days)
        }
        return null
    }

    private fun initViewModel() {
        vm.apply {
            forecastResult.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Init -> {
                    }
                    is Loading -> {
                        isLoading.value = true
                    }
                    is Success -> {
                        isLoading.value = false
                        hideKeyboard(binding.searchView)
                    }
                    is Failure -> {
                        isLoading.value = false
                        hideKeyboard(binding.searchView)
                        showErrorMessage(
                            binding.mainFragmentCoordinator,
                            it.message,
                            resources
                        ) { getWeatherForecast() }
                    }
                }
            })

            emptyDataText.observe(viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty() && vm.forecastResult.value !is Init) {
                    showErrorMessage(binding.mainFragmentCoordinator, it, resources)
                }
            })

            locationRoomDataList.observe(viewLifecycleOwner, Observer { locationlist ->
                invalidateSearchField(locationlist.map { it.location_name })
            })

            invalidateAdapterEvent.observe(viewLifecycleOwner, Observer {
                invalidateSearchField(vm.locationRoomDataList.value!!.map { it.location_name })
            })

            actionMessageEvent.observe(viewLifecycleOwner, Observer {
                toast(it)
            })
        }
    }

    private fun invalidateSearchField(locationList: List<String>) {
        arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            locationList
        )
        binding.locationField.apply {
            dropDownAnchor = R.id.searchView
            threshold = 1
            setAdapter(arrayAdapter)
            setOnItemClickListener { _, _, _, _ ->
                vm.getWeatherForecast()
            }
        }
    }
}
