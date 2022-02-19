package com.ozcoin.cookiepang.ui.editprofile

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.userinfo.UserInfo
import com.ozcoin.cookiepang.domain.userinfo.UserInfoRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DialogUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.EventFlow
import com.ozcoin.cookiepang.utils.MutableEventFlow
import com.ozcoin.cookiepang.utils.TextInputUtil
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.asEventFlow
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel() {

    private val _editProfileEventFlow = MutableEventFlow<EditProfileEvent>()
    val editProfileEventFlow: EventFlow<EditProfileEvent>
        get() = _editProfileEventFlow.asEventFlow()

    private val _introduceMaxLengthCaption = MutableStateFlow<String?>(null)
    val introduceMaxLengthCaption: StateFlow<String?>
        get() = _introduceMaxLengthCaption.asStateFlow()

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?>
        get() = _userInfo

    lateinit var eventObserver: EventObserver
    lateinit var uiStateObserver: UiStateObserver

    fun loadLoginUserInfo() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            val loginUser = userRepository.getLoginUser()
            if (loginUser != null) {
                val result = userInfoRepository.getUserInfo(loginUser.userId)
                if (result is DataResult.OnSuccess) {
                    _userInfo.emit(result.response)
                    uiStateObserver.update(UiState.OnSuccess)
                } else {
                    uiStateObserver.update(UiState.OnFail)
                    navigateUp()
                }
            } else {
                uiStateObserver.update(UiState.OnFail)
                navigateUp()
            }
        }
    }

    fun emitIntroduceLength(length: Int) {
        val caption = if (length >= 20)
            TextInputUtil.getMaxLengthFormattedString(length, 25)
        else
            null

        viewModelScope.launch {
            _introduceMaxLengthCaption.emit(caption)
        }
    }

    private fun showChangeImgDialog(isThumbnail: Boolean) {
        eventObserver.update(
            Event.ShowDialog(
                DialogUtil.getChangeImg(),
                callback = {
                    if (it) openGallery(isThumbnail) else openCamera(isThumbnail)
                }
            )
        )
    }

    private fun openCamera(isThumbnail: Boolean) {
        viewModelScope.launch {
            _editProfileEventFlow.emit(EditProfileEvent.OpenCamera(isThumbnail))
        }
    }

    private fun openGallery(isThumbnail: Boolean) {
        viewModelScope.launch {
            _editProfileEventFlow.emit(EditProfileEvent.OpenGallery(isThumbnail))
        }
    }

    fun updateUserThumbnail(bitmap: Bitmap?) {
        viewModelScope.launch {
            _userInfo.first()?.updateThumbnailImg = bitmap
        }
    }

    fun updateUserBackgroundImg(bitmap: Bitmap?) {
        viewModelScope.launch {
            _userInfo.first()?.updateProfileBackgroundImg = bitmap
        }
    }

    fun clickEditBackgroundImg() {
        showChangeImgDialog(false)
    }

    fun clickEditThumbnailImg() {
        showChangeImgDialog(true)
    }

    private fun updateUserInfo() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            userInfo.value?.let {
                if (userInfoRepository.updateUserInfo(it)) {
                    navigateUp()
                    uiStateObserver.update(UiState.OnSuccess)
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            } ?: uiStateObserver.update(UiState.OnFail)
        }
    }

    fun clickSaveUserInfo() {
        updateUserInfo()
    }
}
