package com.ozcoin.cookiepang.ui.home

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.feed.Feed
import com.ozcoin.cookiepang.domain.feed.FeedRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.TitleClickListener
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository,
    private val userRepository: UserRepository,
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _userCategoryList = MutableStateFlow<List<UserCategory>>(emptyList())
    val userCategoryList: StateFlow<List<UserCategory>>
        get() = _userCategoryList.asStateFlow()

    private val _feedList = MutableStateFlow<List<Feed>>(emptyList())
    val feedList: StateFlow<List<Feed>>
        get() = _feedList

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    lateinit var uiStateObserver: UiStateObserver

    fun getUserCategoryList(retryCnt: Int = 3) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getLoginUser()
            if (user != null) {
                val result = userCategoryRepository.getUserCategory(user.userId)
                if (result is DataResult.OnSuccess) {
                    Timber.d("getUserCategoryList onSuccess")

                    val list = addAllTypeToUserCategoryList(result.response)
                    _userCategoryList.emit(list)
                    getFeedList(UserCategory.typeAll())
                } else {
                    Timber.d("getUserCategoryList onFail(retryCnt: $retryCnt)")

                    if (retryCnt > 0) {
                        getUserCategoryList(retryCnt - 1)
                    }
                }
            } else {
                Timber.d("User is null(retryCnt: $retryCnt)")
                if (retryCnt > 0) {
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
            uiStateObserver.update(UiState.OnLoading)

            val result = userRepository.getLoginUser()
                ?.let { feedRepository.getFeedList(it.userId, userCategory) }
            if (result is DataResult.OnSuccess) {
                Timber.d("getFeedList onSuccess")

                _feedList.emit(result.response)
                uiStateObserver.update(UiState.OnSuccess)
            } else {
                Timber.d("getFeedList onFail")

                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    fun navigateToSelectCategory() {
        navigateTo(HomeFragmentDirections.actionSelectCategory())
    }

    fun navigateToCookieDetail(cookieId: String) {
        navigateTo(HomeFragmentDirections.actionCookieDetail(cookieId))
    }

    fun navigateToUserProfile(userId: String) {
        navigateTo(HomeFragmentDirections.actionMyHome(userId))
    }
}
