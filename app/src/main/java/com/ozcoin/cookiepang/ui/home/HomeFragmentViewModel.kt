package com.ozcoin.cookiepang.ui.home

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.feed.Feed
import com.ozcoin.cookiepang.domain.feed.FeedRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository,
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _userCategoryList = MutableStateFlow<List<UserCategory>>(emptyList())
    val userCategoryList: StateFlow<List<UserCategory>>
        get() = _userCategoryList.asStateFlow()

    private val _feedList = MutableStateFlow<List<Feed>>(emptyList())
    val feedList: StateFlow<List<Feed>>
        get() = _feedList

    var sendUiState: ((UiState) -> Unit)? = null

    fun getUserCategoryList(retryCnt: Int = 3) {
        sendUiState?.invoke(UiState.OnLoading)

        viewModelScope.launch {
            val result = userCategoryRepository.getUserCategory()
            if (result is DataResult.OnSuccess) {
                Timber.d("getUserCategoryList onSuccess")

                val list = addAllTypeToUserCategoryList(result.response)
                _userCategoryList.emit(list)
                sendUiState?.invoke(UiState.OnSuccess)
                getFeedList(UserCategory.typeAll())
            } else {
                Timber.d("getUserCategoryList onFail(retryCnt: $retryCnt)")

                if (retryCnt == 0) {
                    sendUiState?.invoke(UiState.OnFail)
                } else {
                    getUserCategoryList(retryCnt - 1)
                }
            }
        }
    }

    fun restoreUserCategoryList(list: List<UserCategory>) {
        viewModelScope.launch {
            _userCategoryList.emit(list)
        }
    }

    fun restoreFeedList(list: List<Feed>) {
        viewModelScope.launch {
            _feedList.emit(list)
        }
    }

    private fun addAllTypeToUserCategoryList(list: List<UserCategory>): List<UserCategory> {
        return list.toMutableList().apply {
            add(0, UserCategory.typeAll())
        }.toList()
    }

    fun getFeedList(userCategory: UserCategory) {
        viewModelScope.launch {
            sendUiState?.invoke(UiState.OnLoading)

            val result = feedRepository.getFeedList(userCategory)
            if (result is DataResult.OnSuccess) {
                Timber.d("getFeedList onSuccess")

                _feedList.emit(result.response)
                sendUiState?.invoke(UiState.OnSuccess)
            } else {
                Timber.d("getFeedList onFail")

                sendUiState?.invoke(UiState.OnFail)
            }
        }
    }

    fun navigateToSelectCategory() {
        navigateTo(HomeFragmentDirections.actionSelectCategory())
    }
}
