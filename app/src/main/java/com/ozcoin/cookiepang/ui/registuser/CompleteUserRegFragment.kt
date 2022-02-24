package com.ozcoin.cookiepang.ui.registuser

import androidx.activity.OnBackPressedCallback
import com.ozcoin.cookiepang.MyApplication
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
        addBackKeyListener()
        binding.tvMoveToHomeBtn.setOnClickListener { navigateToMain() }
        binding.tvMoveToMyProfileBtn.setOnClickListener { navigateToMyHome() }
    }

    private fun addBackKeyListener() {
        onBackKeyPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
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

    private fun navigateToMyHome() {
        (requireActivity().application as? MyApplication)?.onBoardingPageSelectedMyHome = true
        handleEvent(
            Event.Nav.To(CompleteUserRegFragmentDirections.actionMain())
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
