package com.ozcoin.cookiepang.ui.selectcategory

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSelectCategoryBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SelectCategoryFragment : BaseFragment<FragmentSelectCategoryBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
    private val selectCategoryFragmentViewModel by viewModels<SelectCategoryFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_select_category
    }

    override fun initView() {
        with(binding) {
            val args: SelectCategoryFragmentArgs by navArgs()
            userName = args.userName
            viewModel = selectCategoryFragmentViewModel
        }
    }

    override fun initObserve() {
        lifecycleScope.launch {
            selectCategoryFragmentViewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    override fun init() {
        selectCategoryFragmentViewModel.finishActivity = splashActivityViewModel::finishActivity
    }

    override fun initListener() {
    }
}
