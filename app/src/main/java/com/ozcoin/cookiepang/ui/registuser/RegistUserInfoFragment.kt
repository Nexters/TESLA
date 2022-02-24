package com.ozcoin.cookiepang.ui.registuser

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentRegistUserInfoBinding
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistUserInfoFragment : BaseFragment<FragmentRegistUserInfoBinding>() {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val registUserInfoFragmentViewModel by viewModels<RegistUserInfoFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_regist_user_info
    }

    override fun initView() {
        with(binding) {
            user = loginViewModel.user
            viewModel = registUserInfoFragmentViewModel
        }
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            registUserInfoFragmentViewModel.clickBack()
        }
    }

    override fun initObserve() {
        observeEvent(registUserInfoFragmentViewModel)
        registUserInfoFragmentViewModel.uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
    }

    override fun init() {
    }
}
