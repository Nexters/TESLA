package com.ozcoin.cookiepang.ui.home

import android.os.Bundle
import android.view.View
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentHomeBinding
import com.ozcoin.cookiepang.ui.MainVm
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val mainVm by sharedViewModel<MainVm>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        mainVm.setUseBottomNav(true)
        mainVm.setUseTitleBar(true)
    }


}