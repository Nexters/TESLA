package com.ozcoin.cookiepang.ui.splash

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.MyApplication
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSplashBinding
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                delay(1500L)
            }

            if (loginViewModel.isUserLogin()) {
                if (loginViewModel.isFinishOnBoarding()) {
                    navigateToMain()
                } else {
                    navigateToOnBoarding()
                }
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToMain() {
        handleEvent(
            Event.Nav.To(SplashFragmentDirections.actionMain())
        )
    }

    private fun navigateToOnBoarding() {
        (requireActivity().application as? MyApplication)?.userDidNotFinishOnBoarding = true
        handleEvent(
            Event.Nav.To(SplashFragmentDirections.actionLogin())
        )
    }

    private fun navigateToLogin() {
        handleEvent(
            Event.Nav.To(SplashFragmentDirections.actionLogin())
        )
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
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
