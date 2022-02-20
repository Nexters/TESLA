package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.EventFlow
import com.ozcoin.cookiepang.utils.MutableEventFlow
import com.ozcoin.cookiepang.utils.TextInputUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private fun navigateToRegistInfo() {
        navigateTo(RegistIDFragmentDirections.actionRegistUserInfo())
    }

    fun emitProfileIDLength(length: Int) {
        val caption = if (length >= 10)
            TextInputUtil.getMaxLengthFormattedString(length, 15)
        else
            null
        viewModelScope.launch {
            _profileIDMaxLengthCaption.emit(caption)
        }
    }

    private suspend fun isAvailableProfileId(): Boolean {
        return userRepository.regUser(user)
    }

    private suspend fun checkAvailableProfileId(): Boolean {
        var result = false
        if (user.profileID.isNotBlank()) {
            result = isAvailableProfileId()
        } else {
            Timber.d("profileId is empty")
        }

        return result
    }

    private fun registId() {
        viewModelScope.launch {
            if (checkAvailableProfileId()) {
                navigateToRegistInfo()
            } else {
                _registIdEventFlow.emit(RegistIDEvent.ProfileIdNotAvailable)
                _eventFlow.emit(Event.ShowToast("사용할 수 없습니다."))
            }
        }
    }

    fun clickRegistID() {
        registId()
    }
}
