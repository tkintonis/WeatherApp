package com.example.weatherapp.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.main.current.CurrentForecastFragment
import com.example.weatherapp.main.fivedays.FiveDaysForecastFragment
import java.lang.IllegalStateException

class StateAdapter(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val itemCounter = 2
    private val currentForecastFragment =
        CurrentForecastFragment()
    private val fiveDaysForecastFragment = FiveDaysForecastFragment()

    override fun getItemCount(): Int {
        return itemCounter
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> currentForecastFragment
            1 -> fiveDaysForecastFragment
            else -> throw IllegalStateException()
        }
    }

}