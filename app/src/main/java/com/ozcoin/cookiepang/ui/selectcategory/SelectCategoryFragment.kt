package com.ozcoin.cookiepang.ui.selectcategory

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSelectCategoryBinding
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCategoryFragment: BaseFragment<FragmentSelectCategoryBinding>() {

    private val splashActivityViewModel by sharedViewModel<SplashActivityViewModel>()
    private val selectCategoryFragmentViewModel by viewModel<SelectCategoryFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_select_category
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserve()
        init()
    }

    private fun initView() {
        with(binding) {
            val args : SelectCategoryFragmentArgs by navArgs()
            userName = args.userName
            viewModel = selectCategoryFragmentViewModel
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            selectCategoryFragmentViewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    private fun init() {
        selectCategoryFragmentViewModel.finishActivity = splashActivityViewModel::finishActivity
    }

}
