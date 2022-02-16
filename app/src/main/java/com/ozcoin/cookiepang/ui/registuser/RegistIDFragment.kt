package com.ozcoin.cookiepang.ui.registuser

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentRegistIdBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistIDFragment : BaseFragment<FragmentRegistIdBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
    private val registIDFragmentViewModel by viewModels<RegistIDFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_regist_id
    }

    override fun initView() {
        with(binding) {
            user = splashActivityViewModel.user
            viewModel = registIDFragmentViewModel
            showSkipBtn = false
        }
    }

    override fun initListener() {
        binding.etProfileId.addTextChangedListener {
            viewLifecycleScope.launch { registIDFragmentViewModel.emitProfileIDLength(it?.length ?: 0) }
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
            splashActivityViewModel.user.profileID
        }
    }
}
