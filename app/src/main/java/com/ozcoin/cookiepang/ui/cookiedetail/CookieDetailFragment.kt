package com.ozcoin.cookiepang.ui.cookiedetail

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.CookieHistoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCookieDetailBinding
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SpaceItemDecoration
import com.ozcoin.cookiepang.utils.observer.ActivityEventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CookieDetailFragment : BaseFragment<FragmentCookieDetailBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val cookieDetailViewModel by viewModels<CookieDetailViewModel>()

    private lateinit var cookieHistoryListAdapter: CookieHistoryListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_cookie_detail
    }

    override fun initView() {
        with(binding) {
            isShareAvailable = true
        }

        setupCookieHistoryList()
    }

    private fun setupCookieHistoryList() {
        with(binding.rvCookieHistory) {
            addItemDecoration(
                SpaceItemDecoration(16.toDp())
            )

            cookieHistoryListAdapter = CookieHistoryListAdapter()
            adapter = cookieHistoryListAdapter
        }
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            cookieDetailViewModel.clickBack()
        }
        cookieDetailViewModel.uiStateObserver =
            UiStateObserver(mainActivityViewModel::updateUiState)
    }

    override fun initObserve() {
        observeEvent(cookieDetailViewModel)
        with(cookieDetailViewModel) {
            activityEventObserver = ActivityEventObserver(mainActivityViewModel::updateEvent)
            lifecycleScope.launch {
                cookieDetail.collect {
                    if (it != null) updateCookieDetail(it)
                }
            }
        }
    }

    override fun init() {
        getCookieDetail()
    }

    private fun getCookieDetail() {
        val cookieId = getCookieId()
        if (cookieId.isNotEmpty()) {
            lifecycleScope.launch {
                cookieDetailViewModel.getCookieDetail(cookieId)
            }
        } else {
            cookieDetailViewModel.clickBack()
        }
    }

    private fun updateCookieDetail(cookieDetail: CookieDetail) {
        binding.cookieDetail = cookieDetail
        cookieHistoryListAdapter.updateList(cookieDetail.cookieHistory)
    }

    private fun getCookieId(): String {
        val args by navArgs<CookieDetailFragmentArgs>()
        return args.cookieId
    }
}
