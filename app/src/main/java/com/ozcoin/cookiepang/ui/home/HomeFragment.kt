package com.ozcoin.cookiepang.ui.home

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.FeedListAdapter
import com.ozcoin.cookiepang.adapter.UserCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentHomeBinding
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SingleLineItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
            adapter = UserCategoryListAdapter()
        }
    }

    private fun setUpFeedList() {
        with(binding.rvFeed) {
            addItemDecoration(
                SingleLineItemDecoration(
                    1.toDp(),
                    ContextCompat.getColor(requireContext(), R.color.gray_30_sur2_bg2)
                )
            )
            adapter = FeedListAdapter()
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

    override fun initView() {
        with(binding) {
        }

        setUpCategoryList()
        setUpFeedList()
    }

    override fun initListener() {
        homeFragmentViewModel.sendUiState = mainActivityViewModel::sendUiState
    }

    override fun initObserve() {
        with(homeFragmentViewModel) {
            lifecycleScope.launch {
                eventFlow.collect { handleEvent(it) }
            }
        }
    }

    override fun init() {
    }
}
