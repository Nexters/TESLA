package com.ozcoin.cookiepang.ui.login

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentLoginBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
    private val loginFragmentViewModel by viewModels<LoginFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        with(binding) {
            viewModel = loginFragmentViewModel
        }
    }

    override fun initObserve() {
        lifecycleScope.launch {
            loginFragmentViewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    override fun initListener() {
        lifecycle.addObserver(loginFragmentViewModel)
    }

    override fun init() {
        loginFragmentViewModel.regUserAddress = splashActivityViewModel::setUserAddress
    }
}
