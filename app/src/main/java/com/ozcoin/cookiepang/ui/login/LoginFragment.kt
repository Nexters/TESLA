package com.ozcoin.cookiepang.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentLoginBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    private val loginFragmentViewModel by viewModel<LoginFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserve()
    }

    private fun initView() {
        with(binding) {
            viewModel = loginFragmentViewModel
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            loginFragmentViewModel.eventFlow.collect { handleEvent(it) }
        }
    }


}