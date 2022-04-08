package com.ozcoin.cookiepang.ui.cookiedetail

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.CookieHistoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCookieDetailBinding
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SpaceItemDecoration
import com.ozcoin.cookiepang.utils.DialogUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CookieDetailFragment : BaseFragment<FragmentCookieDetailBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_cookie_detail
    }

    override fun initView() {
    }

    override fun init() {
        val cookieDetailViewModel by viewModels<CookieDetailViewModel>()
        val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

        with(binding) {
            isShareAvailable = true
            titleClickListener = cookieDetailViewModel.titleClickListener
            includeTitleLayout.ivBackBtn.setOnClickListener { navigationUp() }

            bindState(
                uiState = cookieDetailViewModel.state,
                uiAction = cookieDetailViewModel.action,
                loadState = mainActivityViewModel::updateUiState,
                event = mainActivityViewModel::updateEvent
            )
        }
    }

    private fun FragmentCookieDetailBinding.bindState(
        uiState: StateFlow<CookieDetailUiState>,
        uiAction: (CookieDetailUiAction) -> Unit,
        loadState: (UiState) -> Unit,
        event: (Event) -> Unit
    ) {

        bindLoadState(
            uiState = uiState,
            loadState = loadState,
            showDialogEvent = event
        )

        bindCookieDetail(
            uiState = uiState,
            loadCookieDetail = uiAction
        )

        bindCookieHistory(
            uiState = uiState
        )
    }

    private fun FragmentCookieDetailBinding.bindLoadState(
        uiState: StateFlow<CookieDetailUiState>,
        loadState: (UiState) -> Unit,
        showDialogEvent: (Event.ShowDialog) -> Unit
    ) {
        val cookieDetailLoadState = uiState
            .map { it.loadState }
            .filterNotNull()
            .distinctUntilChanged()

        val isLoadFail = cookieDetailLoadState
            .filterIsInstance<UiState.OnFail>()

        viewLifecycleScope.launch {
            cookieDetailLoadState.collect {
                loadState(it)
            }
        }

        viewLifecycleScope.launch {
            isLoadFail.collect {
                navigationUp()
            }
        }

        viewLifecycleScope.launch {
            uiState
                .map { it.isHiddenCookieByOtherUser }
                .distinctUntilChanged()
                .collect {
                    if (it) showIsHiddenCookieDialog(showDialogEvent)
                }
        }
    }

    private fun FragmentCookieDetailBinding.bindCookieDetail(
        uiState: StateFlow<CookieDetailUiState>,
        loadCookieDetail: (CookieDetailUiAction.LoadCookieDetail) -> Unit,
        toggleCookieStateChangeListener: (CookieDetailUiAction) -> Unit
    ) {
        loadCookieDetail(CookieDetailUiAction.LoadCookieDetail(getRequestCookieId()))

        tvHideOpenBtn.setOnClickListener {
            uiState.value.cookieDetail?.let { cookieDetail ->
                if (cookieDetail.isHidden) {
                } else {
                    toggleCookieStateChangeListener(CookieDetailUiAction.HideCookie(cookieDetail))
                }
            }
        }

        viewLifecycleScope.launch {
            uiState
                .map { it.cookieDetail }
                .filterNotNull()
                .distinctUntilChanged()
                .collect {
                    cookieDetail = it
                }
        }
    }

    private fun FragmentCookieDetailBinding.bindCookieHistory(
        uiState: StateFlow<CookieDetailUiState>
    ) {
        val cookieHistoryListAdapter = CookieHistoryListAdapter()

        rvCookieHistory.addItemDecoration(SpaceItemDecoration(16.toDp()))
        rvCookieHistory.adapter = cookieHistoryListAdapter

        viewLifecycleScope.launch {
            uiState
                .map { it.cookieDetail?.cookieHistory }
                .filterNotNull()
                .collect {
                    cookieHistoryListAdapter.submitList(it)
                }
        }
    }

    private fun navigationUp() {
        Timber.d("navigate Up")
        findNavController().navigateUp()
    }

    private fun showIsHiddenCookieDialog(showDialogEvent: (Event.ShowDialog) -> Unit) {
        showDialogEvent(
            Event.ShowDialog(
                DialogUtil.getIsHiddenCookieContents(),
                callback = {
                    if (it) navigationUp()
                }
            )
        )
    }

    override fun initListener() {
    }

    private fun getRequestCookieId(): String {
        Timber.tag(">>>").d("getRequestCookieId()")
        val args by navArgs<CookieDetailFragmentArgs>()
        return args.cookieId
    }
}
