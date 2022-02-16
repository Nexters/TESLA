package com.ozcoin.cookiepang.ui.editcookie

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.UserCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentEditCookieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCookieFragment : BaseFragment<FragmentEditCookieBinding>() {

    private val editCookieFragmentViewModel by viewModels<EditCookieFragmentViewModel>()
    private lateinit var userCategoryListAdapter: UserCategoryListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_cookie
    }

    override fun initView() {
        setupUserCategoryList()
    }

    private fun setupUserCategoryList() {
        with(binding.rvCategory) {
            layoutManager = StaggeredGridLayoutManager(
                1,
                LinearLayoutManager.HORIZONTAL
            )
            userCategoryListAdapter = UserCategoryListAdapter().apply {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

                onItemClick = {
                    if (it != null) {
                    } else {
                    }
                }
            }
            adapter = userCategoryListAdapter
        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
        observeEvent(editCookieFragmentViewModel)
        observeUserCategory()
    }

    private fun observeUserCategory() {
        lifecycleScope.launch {
            editCookieFragmentViewModel.userCategoryList.collect {
                userCategoryListAdapter.updateList(it)
            }
        }
    }

    override fun init() {
        editCookieFragmentViewModel.getUserCategoryList()
    }
}
