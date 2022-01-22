package com.ozcoin.cookiepang.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.utils.Event
import timber.log.Timber

abstract class BaseFragment<T: ViewDataBinding> : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutRes() : Int

    protected lateinit var binding : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    protected fun animSlideUpContents() {
        with(binding.root) {
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_up))
        }
    }

    protected fun handleEvent(event: Event) {
        when(event) {
            is Event.Nav -> {
                when(event) {
                    is Event.Nav.To -> {
                        handleNavTo(event.action)
                    }
                }
            }
        }
    }

    private fun handleNavTo(action : NavDirections) {
        Timber.d("navigate to : ${action.javaClass.simpleName}")
        findNavController().navigate(action)
    }

}