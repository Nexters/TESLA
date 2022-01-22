package com.ozcoin.cookiepang.ui.home

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentHomeBinding
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val mainVm by sharedViewModel<MainActivityViewModel>()
    private val homeFragmentViewModel by stateViewModel<HomeFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        animSlideUpContents()
    }

}