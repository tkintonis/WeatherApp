package com.example.weatherapp.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.databinding.SplashActivityLayoutBinding
import com.example.weatherapp.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class SplashScreenActivity : AppCompatActivity() {

    private val vm: SplashViewModel by stateViewModel()
    private lateinit var binding: SplashActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity_layout)
        initBinding()
        initViewModel()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@SplashScreenActivity
            vm = this@SplashScreenActivity.vm
        }
    }

    private fun initViewModel() {
        vm.apply {
            toMainScreenEvent.observe(this@SplashScreenActivity, Observer {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
        }
    }
}