package com.ozcoin.cookiepang.ui.editcookie

import android.view.MotionEvent
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.UserCategoryListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentEditCookieBinding
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.ui.divider.SpaceItemDecoration
import com.ozcoin.cookiepang.utils.SpinnerClickListener
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCookieFragment : BaseFragment<FragmentEditCookieBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val editCookieFragmentViewModel by viewModels<EditCookieFragmentViewModel>()

    private lateinit var userCategoryListAdapter: UserCategoryListAdapter
    private lateinit var spinnerClickListener: SpinnerClickListener

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_cookie
    }

    override fun initView() {
        with(binding) {
            viewModel = editCookieFragmentViewModel
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
                        editCookieFragmentViewModel.editCookie.value.selectedCategory = it
                    }
                }
            }
            addItemDecoration(SpaceItemDecoration(12.toDp()))
            adapter = userCategoryListAdapter

            addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return editCookieFragmentViewModel.editCookie.value.isEditPricingInfo
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                }
            })
        }
    }

    override fun initListener() {
        setupMaxLengthCaptionListener()
        addBackKeyListener()
    }

    private fun setupSpinnerListener(editCookie: EditCookie) {
        val num = kotlin.runCatching { editCookie.hammerCost.toInt() }.getOrDefault(0)
        spinnerClickListener = SpinnerClickListener(num, viewLifecycleScope)
        binding.spinnerListener = spinnerClickListener

        viewLifecycleScope.launch {
            spinnerClickListener.numValue.collect {
                editCookieFragmentViewModel.editCookie.value.hammerCost = it
            }
        }
    }

    private fun setupMaxLengthCaptionListener() {
        binding.etQuestion.addTextChangedListener {
            editCookieFragmentViewModel.emitQuestionLength(it?.length ?: 0)
        }
        binding.etAnswer.addTextChangedListener {
            editCookieFragmentViewModel.emitAnswerLength(it?.length ?: 0)
        }
    }

    private fun addBackKeyListener() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                editCookieFragmentViewModel.showCloseEditingCookieDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun removeBackKeyListener() {
        onBackPressedCallback.remove()
    }

    override fun initObserve() {
        with(editCookieFragmentViewModel) {
            lifecycle.addObserver(this)
            activityEventObserver = EventObserver(mainActivityViewModel::updateEvent)
            uiStateObserver = UiStateObserver(mainActivityViewModel::updateUiState)
        }
        observeEvent(editCookieFragmentViewModel)
        observeUserCategory()
        observeEditCookieEvent()
    }

    private fun observeEditCookieEvent() {
        viewLifecycleScope.launch {
            editCookieFragmentViewModel.editCookieEventFlow.collect {
                handleEditCookieEvent(it)
            }
        }
    }

    private fun observeUserCategory() {
        viewLifecycleScope.launch {
            editCookieFragmentViewModel.userCategoryList.takeWhile { it.isNotEmpty() }.collect {
                matchUserCategoryListBySelectedCategory(it)
                userCategoryListAdapter.updateList(it)
            }
        }
    }

    private fun matchUserCategoryListBySelectedCategory(list: List<UserCategory>): List<UserCategory> {
        list.find { userCategory ->
            editCookieFragmentViewModel.editCookie.value.selectedCategory?.categoryName == userCategory.categoryName
        }?.run {
            isSelected = true
        }

        return list
    }

    override fun init() {
        val editCookie = getEditCookie()
        if (editCookie != null) {
            setupSpinnerListener(editCookie)
            editCookieFragmentViewModel.setEditCookie(editCookie)
        } else {
            setupSpinnerListener(editCookieFragmentViewModel.editCookie.value)
        }
        editCookieFragmentViewModel.getUserCategoryList()
    }

    private fun getEditCookie(): EditCookie? {
        val args by navArgs<EditCookieFragmentArgs>()
        return args.editCookie
    }

    private fun handleEditCookieEvent(event: EditCookieEvent) {
        when (event) {
            EditCookieEvent.QuestionInfoMissing -> {
                binding.etQuestion.requestFocus()
            }
            EditCookieEvent.AnswerInfoMissing -> {
                binding.etAnswer.requestFocus()
            }
            EditCookieEvent.HammerCostInfoMissing -> {
                binding.includeSpinnerLayout.etNumValue.requestFocus()
            }
            EditCookieEvent.CategoryInfoMissing -> {
                binding.rvCategory.requestFocus()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeBackKeyListener()
    }
}
