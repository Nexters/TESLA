package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.TextInputUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistIDFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _profileID = MutableStateFlow("")
    val profileID: StateFlow<String>
        get() = _profileID

    private val _profileIDMaxLengthCaption = MutableStateFlow<String?>(null)
    val profileIDMaxLengthCaption: StateFlow<String?>
        get() = _profileIDMaxLengthCaption.asStateFlow()

    var setUserProfileID: ((String) -> Unit)? = null
    var getUserProfileID: (() -> String)? = null

    private fun navigateToRegistInfo() {
        viewModelScope.launch {
//            _eventFlow.emit(Event.Nav.To())
        }
    }

    suspend fun emitProfileIDLength(length: Int) {
        val caption = if (length >= 10)
            TextInputUtil.getMaxLengthFormattedString(length, 15)
        else
            null

        _profileIDMaxLengthCaption.emit(caption)
    }

    private suspend fun checkDuplicateProfileID(): Boolean {
        val profileID = getUserProfileID?.invoke()
        return profileID?.let {
            userRepository.checkDuplicateProfileID(it)
        } ?: false
    }

    fun clickRegistID() {
        viewModelScope.launch {
            if (checkDuplicateProfileID()) {
                navigateToRegistInfo()
            }
        }
    }
}
