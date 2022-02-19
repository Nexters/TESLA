package com.ozcoin.cookiepang.ui.registuser

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.SelectCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSelectCategoryBinding
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SingleLineItemDecoration
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectCategoryFragment : BaseFragment<FragmentSelectCategoryBinding>() {

    companion object {
        const val KEY_RESET_USER_CATEGORY = "KEY_RESET_USER_CATEGORY"
    }

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val selectCategoryFragmentViewModel by viewModels<SelectCategoryFragmentViewModel>()

    private lateinit var selectCategoryListAdapter: SelectCategoryListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_select_category
    }

    override fun initView() {
        with(binding) {
            viewModel = selectCategoryFragmentViewModel
        }

        setUpCategoryList()
    }

    private fun setUpCategoryList() {
        with(binding.rvSelectCategory) {
            addItemDecoration(
                SingleLineItemDecoration(
                    1.toDp(),
                    ContextCompat.getColor(requireContext(), R.color.gray_40_t4_ic2_br1)
                )
            )
            selectCategoryListAdapter = SelectCategoryListAdapter().apply {
                onItemClick = {
                    selectCategoryFragmentViewModel.selectedCategories = it
                }
            }
            adapter = selectCategoryListAdapter
        }
    }

    override fun initObserve() {
        observeEvent(selectCategoryFragmentViewModel)
        selectCategoryFragmentViewModel.uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            selectCategoryFragmentViewModel.clickBack()
        }
    }

    override fun init() {
        selectCategoryFragmentViewModel.registrationUser = loginViewModel.user
        viewLifecycleScope.launch {
            selectCategoryListAdapter.updateList(selectCategoryFragmentViewModel.getCategoryList())
        }
    }
}
