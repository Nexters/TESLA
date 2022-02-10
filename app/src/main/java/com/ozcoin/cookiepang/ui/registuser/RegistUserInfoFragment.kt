package com.ozcoin.cookiepang.ui.registuser

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentRegistUserInfoBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistUserInfoFragment : BaseFragment<FragmentRegistUserInfoBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
    private val registUserInfoFragmentViewModel by viewModels<RegistUserInfoFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_regist_user_info
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initObserve() {
    }

    override fun init() {
    }
}
