package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.common.URL_TERMS_OF_USE
import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.onboarding.OnBoardingCookie
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistUserInfoFragmentViewModel @Inject constructor(
    private val cookieRepository: CookieRepository
) : BaseViewModel() {

    lateinit var uiStateObserver: UiStateObserver

    private fun navigateToSelectCategory() {
        navigateTo(RegistUserInfoFragmentDirections.actionSelectCategory())
    }

    private fun showTermOfUser() {
        viewModelScope.launch {
            _eventFlow.emit(Event.ShowWeb(URL_TERMS_OF_USE))
        }
    }

    private fun makeOnBoardingCookie(user: User) {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            val list = convertToOnBoardingCookieList(user)
            if (list.isNotEmpty()) {
                if (cookieRepository.makeOnBoardingCookie(user.userId, list)) {
                    uiStateObserver.update(UiState.OnSuccess)
                    navigateToSelectCategory()
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            } else {
                uiStateObserver.update(UiState.OnFail)
                _eventFlow.emit(Event.ShowToast("답변을 입력하면 쿠키를 만들어 줄게요 !"))
            }
        }
    }

    private fun convertToOnBoardingCookieList(user: User): List<OnBoardingCookie> {
        val list = mutableListOf<OnBoardingCookie>()
        if (user.location.isNotBlank()) {
            list.add(
                OnBoardingCookie(
                    "내가 사는 지역은 어디일까?",
                    user.location
                )
            )
        }
        if (user.height.isNotBlank()) {
            list.add(
                OnBoardingCookie(
                    "나의 키는 몇 cm일까?",
                    user.height
                )
            )
        }
        if (user.job.isNotBlank()) {
            list.add(
                OnBoardingCookie(
                    "내 직업은 무엇일까?",
                    user.job
                )
            )
        }

        return list
    }

    fun clickTermOfUse() {
        showTermOfUser()
    }

    fun clickMakeOnBoardingCookie(user: User) {
        makeOnBoardingCookie(user)
    }
}
