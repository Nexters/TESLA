package com.ozcoin.cookiepang.ui.home

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ozcoin.cookiepang.MyApplication
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
import com.ozcoin.cookiepang.ui.registuser.SelectCategoryFragment
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            userCategoryListAdapter = UserCategoryListAdapter(true).apply {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

                onItemClick = {
                    if (it != null) {
                        homeFragmentViewModel.setSelectedUserCategory(it)
                    } else {
                        (requireActivity().application as? MyApplication)?.requestUserCategoryReset = true
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
            homeFragmentViewModel.feedRefreshFinish = {
                if (it) smoothScrollToPosition(0)
            }

            addItemDecoration(
                SingleLineItemDecoration(
                    1.toDp(),
                    ContextCompat.getColor(requireContext(), R.color.gray_30_sur2_bg2)
                )
            )
            feedListAdapter = FeedListAdapter().apply {
                onItemClick = {
                    homeFragmentViewModel.navigateToCookieDetail(it.cookieId.toString())
                }
                onUserProfileClick = {
                    homeFragmentViewModel.navigateToUserProfile(it.feedUserId)
                }
            }
            adapter = feedListAdapter
        }
        setupEndlessFeedList()
    }

    private fun setupEndlessFeedList() {
        val layoutManager = binding.rvFeed.layoutManager as? LinearLayoutManager
        binding.rvFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCnt = homeFragmentViewModel.feedList.value.size
                val completeVisibleListItemPos = layoutManager?.findLastCompletelyVisibleItemPosition()

                if (completeVisibleListItemPos == totalItemCnt - 3)
                    homeFragmentViewModel.loadMoreFeed()
            }
        })
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

    override fun onStop() {
        super.onStop()
        saveListState()
    }

    private fun saveListState() {
        Timber.d("saveListState()")
        setViewStateUserCategoryList(true)
        setViewStateFeedList(true)
        setViewDataFeedList(true)
        setViewDataUserCategoryList(true)
    }

    private suspend fun isViewDataLoaded(): Boolean = withContext(Dispatchers.Default) {
        val feedList = mainActivityViewModel.savedStateHandle.get<List<Feed>>(
            KEY_VIEW_DATA_USER_CATEGORY_LIST
        )
        val userCategoryList = mainActivityViewModel.savedStateHandle.get<List<UserCategory>>(
            KEY_VIEW_DATA_USER_CATEGORY_LIST
        )

        feedList != null && feedList.isNotEmpty() &&
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
        with(binding) {
            titleClickListener = homeFragmentViewModel.titleClickListener
        }
        setUpUserCategoryList()
        setUpFeedList()
        setupRefreshView()
    }

    private fun setupRefreshView() {
        binding.srlFeed.setColorSchemeResources(R.color.sub_01)
        binding.srlFeed.setProgressBackgroundColorSchemeResource(R.color.gray_20_bg2_sur1)
    }

    override fun initListener() {
        setupRefreshListener()
    }

    private fun setupRefreshListener() {
        binding.srlFeed.setOnRefreshListener {
            homeFragmentViewModel.refreshFeedList()
            binding.srlFeed.isRefreshing = false
        }
    }

    override fun initObserve() {
        observeEvent(homeFragmentViewModel)
        observeUserCategoryList()
        observeFeedList()
        homeFragmentViewModel.uiStateObserver =
            UiStateObserver(mainActivityViewModel::updateUiState)
    }

    private fun observeUserCategoryList() {
        viewLifecycleScope.launch {
            homeFragmentViewModel.userCategoryList.collect {
                Timber.d("collect UserCategoryList(size: ${it.size})")
                userCategoryListAdapter.updateList(it)
            }
        }
    }

    private fun observeFeedList() {
        viewLifecycleScope.launch {
            homeFragmentViewModel.feedList.collect {
                Timber.d("collect FeedList(size: ${it.size})")
                feedListAdapter.updateList(it)
            }
        }
    }

    private fun isMoveToMyHomeFromOnBoarding(): Boolean {
        val result =
            (requireActivity().application as? MyApplication)?.onBoardingPageSelectedMyHome ?: false
        (requireActivity().application as? MyApplication)?.onBoardingPageSelectedMyHome = false
        return result
    }

    private fun navigateToMyHome() {
        Timber.d("navigateToMyHome()")
        val navOption = NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setPopUpTo(R.id.home_dest, false)
        }
        findNavController().navigate(R.id.my_home_dest, null, navOption.build())
    }

    override fun init() {
        if (isMoveToMyHomeFromOnBoarding()) {
            Timber.d("온보딩 화면에서 프로필로 가기 선택")
            navigateToMyHome()
        } else {
            viewLifecycleScope.launch {
                if (itHaveToResetUserCategory()) {
                    binding.rvFeed.smoothScrollToPosition(0)
                    homeFragmentViewModel.loadUserCategoryList()
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
                        homeFragmentViewModel.loadUserCategoryList()
                    }
                }
            }
        }
    }

    private fun itHaveToResetUserCategory(): Boolean {
        val resetCategory = findNavController().currentBackStackEntry?.savedStateHandle?.let {
            val result = it.get<Boolean>(SelectCategoryFragment.KEY_RESET_USER_CATEGORY)
            it.set(SelectCategoryFragment.KEY_RESET_USER_CATEGORY, false)

            result
        } ?: false

        return resetCategory.also { Timber.d("itHaveToResetUserCategory : $it") }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleScope.launch(Dispatchers.Default) {
            delay(100L)
            mainActivityViewModel.showBtmNavView()
        }
    }
}
