package com.ozcoin.cookiepang.ui.registuser

import androidx.activity.addCallback
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCompleteUserRegBinding
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompleteUserRegFragment @Inject constructor() :
    BaseFragment<FragmentCompleteUserRegBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_complete_user_reg
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.tvCheckHowToUseBtn.setOnClickListener {
            handleEvent(
                Event.Nav.To(CompleteUserRegFragmentDirections.actionOnBoarding01())
            )
        }
        setBackKeyListener()
    }

    private fun setBackKeyListener() {
        requireActivity().onBackPressedDispatcher.addCallback {
            handleEvent(
                Event.Nav.To(CompleteUserRegFragmentDirections.actionOnBoarding01())
            )
        }
    }

    override fun initObserve() {
    }

    override fun init() {
    }
}
