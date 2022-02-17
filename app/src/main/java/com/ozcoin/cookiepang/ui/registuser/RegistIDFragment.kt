package com.ozcoin.cookiepang.ui.registuser

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentRegistIdBinding
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistIDFragment : BaseFragment<FragmentRegistIdBinding>() {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val registIDFragmentViewModel by viewModels<RegistIDFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_regist_id
    }

    override fun initView() {
        with(binding) {
            user = loginViewModel.user
            viewModel = registIDFragmentViewModel
            showSkipBtn = false
        }
    }

    override fun initListener() {
        binding.etProfileId.addTextChangedListener {
            registIDFragmentViewModel.emitProfileIDLength(it?.length ?: 0)
        }
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            registIDFragmentViewModel.clickBack()
        }
    }

    override fun initObserve() {
        observeEvent(registIDFragmentViewModel)
    }

    override fun init() {
        registIDFragmentViewModel.getUserProfileID = {
            loginViewModel.user.profileID
        }
    }
}
