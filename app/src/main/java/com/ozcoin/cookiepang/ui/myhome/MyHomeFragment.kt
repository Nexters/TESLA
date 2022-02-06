package com.ozcoin.cookiepang.ui.myhome

import android.os.Bundle
import android.view.View
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentMyHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyHomeFragment : BaseFragment<FragmentMyHomeBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        init()
    }

    private fun initView() {
    }

    private fun initListener() {
    }

    private fun init() {
        animSlideUpContents()
    }
}
