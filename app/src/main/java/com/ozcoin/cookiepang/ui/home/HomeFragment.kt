package com.ozcoin.cookiepang.ui.home

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.CategoryListAdapter
import com.ozcoin.cookiepang.adapter.FeedListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentHomeBinding
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {
        private const val KEY_VIEW_STATE_CATEGORY_LIST = "KEY_VIEW_STATE_CATEGORY_LIST"
        private const val KEY_VIEW_STATE_SCROLL_POS = "KEY_VIEW_STATE_SCROLL_POS"
        private const val KEY_VIEW_STATE_FEED_LIST = "KEY_VIEW_STATE_FEED_LIST"
    }

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    private fun setUpCategoryList() {
        with(binding.rvCategory) {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = CategoryListAdapter()
        }
    }

    private fun setUpFeedList() {
        with(binding.rvFeed) {
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = FeedListAdapter()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.run {
            putParcelable(
                KEY_VIEW_STATE_CATEGORY_LIST,
                binding.rvCategory.layoutManager?.onSaveInstanceState()
            )
            putParcelable(
                KEY_VIEW_STATE_FEED_LIST,
                binding.rvFeed.layoutManager?.onSaveInstanceState()
            )
        }
    }

    override fun onStart() {
        super.onStart()

        restoreListState()
    }

    private fun restoreListState() {
        mainActivityViewModel.savedStateHandle.let {
            binding.rvFeed.layoutManager?.onRestoreInstanceState(
                it.get(KEY_VIEW_STATE_FEED_LIST)
            )
            binding.rvCategory.layoutManager?.onRestoreInstanceState(
                it.get(KEY_VIEW_STATE_CATEGORY_LIST)
            )
//            it.get<Int>(KEY_VIEW_STATE_SCROLL_POS)?.let { posY ->
//                binding.nsvContainerLayout.scrollY = posY
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveListState()
    }

    private fun saveListState() {
        mainActivityViewModel.savedStateHandle.run {
            set(KEY_VIEW_STATE_SCROLL_POS, binding.nsvContainerLayout.scrollY)
            set(KEY_VIEW_STATE_FEED_LIST, binding.rvFeed.layoutManager?.onSaveInstanceState())
            set(
                KEY_VIEW_STATE_CATEGORY_LIST,
                binding.rvCategory.layoutManager?.onSaveInstanceState()
            )
        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
    }

    override fun init() {
    }

    override fun initView() {
        animSlideUpContents()
        setUpCategoryList()
        setUpFeedList()
    }
}
