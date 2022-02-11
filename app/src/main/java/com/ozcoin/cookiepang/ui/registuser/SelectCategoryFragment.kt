package com.ozcoin.cookiepang.ui.registuser

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.SelectCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentSelectCategoryBinding
import com.ozcoin.cookiepang.domain.selectcategory.SelectCategory
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.divider.SingleLineItemDecoration
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SelectCategoryFragment : BaseFragment<FragmentSelectCategoryBinding>() {

    private val splashActivityViewModel by activityViewModels<SplashActivityViewModel>()
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
            selectCategoryListAdapter = SelectCategoryListAdapter()
            adapter = selectCategoryListAdapter
        }
    }

    private fun getSelectCategoryList(): List<SelectCategory> {
        val list = mutableListOf<SelectCategory>()
        repeat(10) {
            list.add(SelectCategory("test$it", false))
        }
        return list
    }

    override fun initObserve() {
        lifecycleScope.launch {
            selectCategoryFragmentViewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            selectCategoryFragmentViewModel.clickBack()
        }
    }

    override fun init() {
        selectCategoryListAdapter.updateList(getSelectCategoryList())
    }
}
