package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DialogUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.EventFlow
import com.ozcoin.cookiepang.utils.MutableEventFlow
import com.ozcoin.cookiepang.utils.TextInputUtil
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistIDFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _registIdEventFlow = MutableEventFlow<RegistIDEvent>()
    val registIdEventFlow: EventFlow<RegistIDEvent>
        get() = _registIdEventFlow

    private val _profileIDMaxLengthCaption = MutableStateFlow<String?>(null)
    val profileIDMaxLengthCaption: StateFlow<String?>
        get() = _profileIDMaxLengthCaption.asStateFlow()

    lateinit var user: User
    lateinit var activityEventObserver: EventObserver
    lateinit var uiStateObserver: UiStateObserver
    var setUser: ((User) -> Unit)? = null

    private fun navigateToRegistInfo() {
        navigateTo(RegistIDFragmentDirections.actionRegistUserInfo())
    }

    fun emitProfileIDLength(length: Int) {
        val caption = if (length >= 5)
            TextInputUtil.getMaxLengthFormattedString(length, 10)
        else
            null
        viewModelScope.launch {
            _profileIDMaxLengthCaption.emit(caption)
        }
    }

    private fun showNotAvailableProfileIdDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getNotAvailableProfileIdContents(),
                callback = {
                    if (it) {
                        viewModelScope.launch {
                            _registIdEventFlow.emit(RegistIDEvent.ProfileIdNotAvailable)
                        }
                    }
                }
            )
        )
    }

    private fun isAvailableProfileId(): Boolean {
        var result = false
        if (user.profileID.isNotBlank()) {
            val regex = "[0-9|a-zA-Zㄱ-ㅎㅏ-ㅣ가-힝^_-]*".toRegex()
            result = user.profileID.matches(regex)
        }

        return result
    }

    private fun registId() {
        viewModelScope.launch {
            if (isAvailableProfileId()) {
                val result = userRepository.regUser(user)
                when (result) {
                    is DataResult.OnSuccess -> {
                        setUser?.invoke(result.response)
                        navigateToRegistInfo()
                    }
                    is DataResult.OnFail -> {
                        if (result.errorCode == 409)
                            _eventFlow.emit(Event.ShowToast("이미 가입된 닉네임 입니다"))
                        else
                            _eventFlow.emit(Event.ShowToast("이미 가입된 유저 입니다\n앱을 다시 시작해주세요"))
                    }
                }
            } else {
                showNotAvailableProfileIdDialog()
            }
        }
    }

    fun clickRegistID() {
        registId()
    }
}
