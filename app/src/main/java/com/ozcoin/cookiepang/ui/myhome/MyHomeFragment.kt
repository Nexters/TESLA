package com.ozcoin.cookiepang.ui.myhome

import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentMyHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyHomeFragment : BaseFragment<FragmentMyHomeBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_home
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun init() {
        animSlideUpContents()
    }

    override fun initObserve() {
    }
}
