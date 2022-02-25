package com.ozcoin.cookiepang.ui.ask

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.UserCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentAskBinding
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SpaceItemDecoration
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AskFragment : BaseFragment<FragmentAskBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val askFragmentViewModel by viewModels<AskFragmentViewModel>()
    private lateinit var userCategoryListAdapter: UserCategoryListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_ask
    }

    override fun initView() {
        with(binding) {
            pageName = "질문 요청하기"
            viewModel = askFragmentViewModel
        }
        setupUserCategoryList()
    }

    private fun setupUserCategoryList() {
        with(binding.rvCategory) {
            layoutManager = StaggeredGridLayoutManager(
                2,
                LinearLayoutManager.HORIZONTAL
            )

            userCategoryListAdapter = UserCategoryListAdapter(false).apply {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

                onItemClick = {
                    if (it != null) {
                        askFragmentViewModel.selectCategory(it)
                    }
                }
            }
            addItemDecoration(SpaceItemDecoration(12.toDp()))
            adapter = userCategoryListAdapter
        }
    }

    override fun initListener() {
        setMaxLengthListener()
    }

    private fun setMaxLengthListener() {
        binding.etQuestion.addTextChangedListener {
            askFragmentViewModel.emitQuestionLength(it?.length ?: 0)
        }
    }

    override fun initObserve() {
        observeEvent(askFragmentViewModel)
        observeUserCategory()
        askFragmentViewModel.uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
    }

    private fun observeUserCategory() {
        viewLifecycleScope.launch {
            askFragmentViewModel.userCategoryList.collect {
                userCategoryListAdapter.updateList(it)
            }
        }
    }

    override fun init() {
        val args by navArgs<AskFragmentArgs>()
        binding.profileId = "@${args.userProfileId}"
        askFragmentViewModel.getUserCategoryList(args.userId)
    }
}
