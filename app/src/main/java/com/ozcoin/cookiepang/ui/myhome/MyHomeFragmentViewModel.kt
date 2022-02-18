package com.ozcoin.cookiepang.ui.myhome

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.cookie.Cookie
import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.userinfo.UserInfo
import com.ozcoin.cookiepang.domain.userinfo.UserInfoRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyHomeFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userInfoRepository: UserInfoRepository,
    private val cookieRepository: CookieRepository
) : BaseViewModel() {

    private val _collectedCookieList = MutableStateFlow<List<Cookie>>(emptyList())
    val collectedCookieList: StateFlow<List<Cookie>>
        get() = _collectedCookieList

    private val _createdCookieList = MutableStateFlow<List<Cookie>>(emptyList())
    val createdCookieList: StateFlow<List<Cookie>>
        get() = _createdCookieList

    private val _questionList = MutableStateFlow<List<Question>>(emptyList())
    val questionList: StateFlow<List<Question>>
        get() = _questionList

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?>
        get() = _userInfo

    lateinit var uiStateObserver: UiStateObserver

    fun loadUserInfo(userId: String?) {
        if (!userId.isNullOrBlank()) {
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                val result = userInfoRepository.getUserInfo(userId)
                if (result is DataResult.OnSuccess) {
                    _userInfo.emit(result.response)

                    val collectedCookie = async { loadCollectedCookie(userId) }
                    val createdCookie = async { loadCreatedCookie(userId) }
                    val question = async { loadQuestions(userId) }

                    collectedCookie.await()
                    createdCookie.await()
                    question.await()

                    uiStateObserver.update(UiState.OnSuccess)
                } else {
                    uiStateObserver.update(UiState.OnFail)
                    navigateUp()
                }
            }
        } else {
            Timber.d("userId is null or blank")

            viewModelScope.launch {
                val loginUser = userRepository.getLoginUser()
                if (loginUser != null && loginUser.userId.isNotEmpty()) {
                    loadUserInfo(loginUser.userId)
                } else {
                    navigateUp()
                }
            }
        }
    }

    private suspend fun loadCollectedCookie(userId: String) {
        cookieRepository.getCollectedCookieList(userId).let {
            if (it is DataResult.OnSuccess)
                _collectedCookieList.emit(it.response)
        }
    }

    private suspend fun loadCreatedCookie(userId: String) {
        cookieRepository.getCreatedCookieList(userId).let {
            if (it is DataResult.OnSuccess)
                _createdCookieList.emit(it.response)
        }
    }

    private suspend fun loadQuestions(userId: String) {
        userRepository.getQuestionList(userId).let {
            if (it is DataResult.OnSuccess)
                _questionList.emit(it.response)
        }
    }

    fun clickAskMe() {
    }

    fun clickEditProfile() {
    }
}
