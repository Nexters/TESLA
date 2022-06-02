package com.ozcoin.cookiepang.ui.login

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.MyApplication
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentLoginBinding
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()
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
        observeEvent(loginFragmentViewModel)
    }

    override fun initListener() {
        lifecycle.addObserver(loginFragmentViewModel)
        loginFragmentViewModel.uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
    }

    override fun init() {
        loginFragmentViewModel.regUserAddress = loginViewModel::setUserAddress
        if (isUserDidNotFinishOnBoarding())
            loginFragmentViewModel.navigateToSelectCategory()
    }

    private fun isUserDidNotFinishOnBoarding(): Boolean {
        val result = (requireActivity().application as? MyApplication)?.userDidNotFinishOnBoarding ?: false
        (requireActivity().application as? MyApplication)?.userDidNotFinishOnBoarding = false
        return result
    }
}
