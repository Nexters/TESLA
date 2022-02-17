package com.ozcoin.cookiepang.ui.registuser

import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCompleteUserRegBinding
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompleteUserRegFragment @Inject constructor() : BaseFragment<FragmentCompleteUserRegBinding>() {

    private lateinit var onBackKeyPressedCallback: OnBackPressedCallback

    override fun getLayoutRes(): Int {
        return R.layout.fragment_complete_user_reg
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.tvCheckHowToUseBtn.setOnClickListener {
            navigateToOnBoarding()
        }
        addBackKeyListener()
    }

    private fun addBackKeyListener() {
        onBackKeyPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToMain()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackKeyPressedCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeBackKeyListener()
    }

    private fun removeBackKeyListener() {
        onBackKeyPressedCallback.remove()
    }

    private fun navigateToOnBoarding() {
        handleEvent(
            Event.Nav.To(CompleteUserRegFragmentDirections.actionOnBoarding01())
        )
    }

    private fun navigateToMain() {
        handleEvent(
            Event.Nav.To(CompleteUserRegFragmentDirections.actionMain())
        )
    }

    override fun initObserve() {
    }

    override fun init() {
    }
}
