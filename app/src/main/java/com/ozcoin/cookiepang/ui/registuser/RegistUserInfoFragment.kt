package com.ozcoin.cookiepang.ui.registuser

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentRegistUserInfoBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistUserInfoFragment : BaseFragment<FragmentRegistUserInfoBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
    private val registUserInfoFragmentViewModel by viewModels<RegistUserInfoFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_regist_user_info
    }

    override fun initView() {
        with(binding) {
            user = splashActivityViewModel.user
            viewModel = registUserInfoFragmentViewModel
        }
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            registUserInfoFragmentViewModel.clickBack()
        }
    }

    override fun initObserve() {
        with(registUserInfoFragmentViewModel) {
            lifecycleScope.launch {
                eventFlow.collect { handleEvent(it) }
            }
        }
    }

    override fun init() {
    }
}
