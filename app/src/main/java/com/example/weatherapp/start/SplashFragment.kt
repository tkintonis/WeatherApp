package com.example.weatherapp.start

import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.SplashLayout1Binding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment() {

    private val vm: SplashViewModel by sharedViewModel()
    private lateinit var binding: SplashLayout1Binding
    private var handler: Handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<SplashLayout1Binding>(
        inflater,
        R.layout.splash_layout_1,
        container,
        false
    ).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        doAnimationsChain()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@SplashFragment
            vm = this@SplashFragment.vm
            startupIcon1.animate().alpha(1f).duration = 2000
        }
    }

    private fun doAnimationsChain() {
        handler.run {
            postDelayed({ changeFrame(R.layout.splash_layout_2) }, 1500)
            postDelayed({ binding.startupIcon2.animate().alpha(1f).duration = 1500 }, 2500)
            postDelayed({ changeFrame(R.layout.splash_layout_3)}, 3000)
            postDelayed({ binding.startupText.animate().alpha(1f).duration = 1500 }, 3500)
            postDelayed({ vm.toMainScreen() }, 6000)
        }
    }

    private fun changeFrame(view: Int) {
        val autoTransition = AutoTransition()
        autoTransition.duration = 1500
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), view)
        TransitionManager.beginDelayedTransition(binding.container, autoTransition)
        constraintSet.applyTo(binding.container)
    }

}