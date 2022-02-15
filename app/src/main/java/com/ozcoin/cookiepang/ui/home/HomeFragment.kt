package com.ozcoin.cookiepang.ui.home

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.FeedListAdapter
import com.ozcoin.cookiepang.adapter.UserCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentHomeBinding
import com.ozcoin.cookiepang.domain.feed.Feed
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SingleLineItemDecoration
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {
        private const val KEY_VIEW_STATE_USER_CATEGORY_LIST = "KEY_VIEW_STATE_CATEGORY_LIST"
        private const val KEY_VIEW_STATE_FEED_LIST = "KEY_VIEW_STATE_FEED_LIST"
        private const val KEY_VIEW_DATA_USER_CATEGORY_LIST = "KEY_VIEW_DATA_USER_CATEGORY_LIST"
        private const val KEY_VIEW_DATA_FEED_LIST = "KEY_VIEW_DATA_FEED_LIST"
    }

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()

    private lateinit var userCategoryListAdapter: UserCategoryListAdapter
    private lateinit var feedListAdapter: FeedListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    private fun setUpUserCategoryList() {
        with(binding.rvCategory) {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            userCategoryListAdapter = UserCategoryListAdapter().apply {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

                onItemClick = {
                    if (it != null) {
                        homeFragmentViewModel.getFeedList(it)
                        binding.rvFeed.smoothScrollToPosition(0)
                    } else {
                        mainActivityViewModel.hideBtmNavView()
                        homeFragmentViewModel.navigateToSelectCategory()
                    }
                }
            }
            adapter = userCategoryListAdapter
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
            feedListAdapter = FeedListAdapter().apply {
                onItemClick = {
                    homeFragmentViewModel.navigateToCookieDetail(it.id)
                }
            }
            adapter = feedListAdapter
        }
    }

    private fun restoreListState() {
        Timber.d("restoreListState()")

        mainActivityViewModel.savedStateHandle.let {
            homeFragmentViewModel.restoreUserCategoryList(
                it[KEY_VIEW_DATA_USER_CATEGORY_LIST] ?: emptyList()
            )
            homeFragmentViewModel.restoreFeedList(
                it[KEY_VIEW_DATA_FEED_LIST] ?: emptyList()
            )
            binding.rvFeed.layoutManager?.onRestoreInstanceState(
                it.get(KEY_VIEW_STATE_FEED_LIST)
            )
            binding.rvCategory.layoutManager?.onRestoreInstanceState(
                it.get(KEY_VIEW_STATE_USER_CATEGORY_LIST)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveListState()
    }

    private fun saveListState() {
        Timber.d("saveListState()")
        setViewStateUserCategoryList(true)
        setViewStateFeedList(true)
        setViewDataFeedList(true)
        setViewDataUserCategoryList(true)
    }

    private fun isViewDataLoaded(): Boolean {
        val feedList = mainActivityViewModel.savedStateHandle.get<List<Feed>>(
            KEY_VIEW_DATA_USER_CATEGORY_LIST
        )
        val userCategoryList = mainActivityViewModel.savedStateHandle.get<List<UserCategory>>(
            KEY_VIEW_DATA_USER_CATEGORY_LIST
        )

        return feedList != null && feedList.isNotEmpty() &&
            userCategoryList != null && userCategoryList.isNotEmpty()
    }

    private fun setViewStateFeedList(saveState: Boolean) {
        val viewState = if (saveState) binding.rvFeed.layoutManager?.onSaveInstanceState() else null

        mainActivityViewModel.savedStateHandle.set(
            KEY_VIEW_STATE_FEED_LIST,
            viewState
        )
    }

    private fun setViewStateUserCategoryList(saveState: Boolean) {
        val viewState = if (saveState) {
            binding.rvCategory.layoutManager?.onSaveInstanceState()
        } else {
            null
        }

        mainActivityViewModel.savedStateHandle.set(
            KEY_VIEW_STATE_USER_CATEGORY_LIST,
            viewState
        )
    }

    private fun setViewDataUserCategoryList(saveState: Boolean) {
        val viewData = if (saveState) {
            homeFragmentViewModel.userCategoryList.value
        } else {
            null
        }

        mainActivityViewModel.savedStateHandle.set(
            KEY_VIEW_DATA_USER_CATEGORY_LIST,
            viewData
        )
    }

    private fun setViewDataFeedList(saveState: Boolean) {
        val viewData = if (saveState) {
            homeFragmentViewModel.feedList.value
        } else {
            null
        }

        mainActivityViewModel.savedStateHandle.set(
            KEY_VIEW_DATA_FEED_LIST,
            viewData
        )
    }

    override fun initView() {
        setUpUserCategoryList()
        setUpFeedList()
    }

    override fun initListener() {
        homeFragmentViewModel.uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
    }

    override fun initObserve() {
        observeEvent(homeFragmentViewModel)
        observeUserCategoryList()
        observeFeedList()
    }

    private fun observeUserCategoryList() {
        lifecycleScope.launch {
            homeFragmentViewModel.userCategoryList.collect {
                Timber.d("collect UserCategoryList(size: ${it.size})")
                userCategoryListAdapter.updateList(it)
            }
        }
    }

    private fun observeFeedList() {
        lifecycleScope.launch {
            homeFragmentViewModel.feedList.collect {
                Timber.d("collect FeedList(size: ${it.size})")
                feedListAdapter.updateList(it)
            }
        }
    }

    override fun init() {
        if (itHaveToResetUserCategory()) {
            Timber.d("is User reset Category")
            homeFragmentViewModel.getUserCategoryList()
        } else {
            if (isViewDataLoaded()) {
                Timber.d("is view data loaded")
                if (homeFragmentViewModel.userCategoryList.value.isEmpty()) {
                    Timber.d("viewModel data not exist, restore list")
                    restoreListState()
                } else {
                    Timber.d("viewModel data exist")
                }
            } else {
                Timber.d("is not view data loaded, so get list data")
                homeFragmentViewModel.getUserCategoryList()
            }
        }
    }

    private fun itHaveToResetUserCategory(): Boolean {
        val args by navArgs<HomeFragmentArgs>()
        return args.refreshUserCategory
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Default) {
            delay(200)
            mainActivityViewModel.showBtmNavView()
        }
    }
}
