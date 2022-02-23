package com.ozcoin.cookiepang.ui.editprofile

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DialogUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.EventFlow
import com.ozcoin.cookiepang.utils.MutableEventFlow
import com.ozcoin.cookiepang.utils.TextInputUtil
import com.ozcoin.cookiepang.utils.TitleClickListener
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
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _editProfileEventFlow = MutableEventFlow<EditProfileEvent>()
    val editProfileEventFlow: EventFlow<EditProfileEvent>
        get() = _editProfileEventFlow.asEventFlow()

    private val _introduceMaxLengthCaption = MutableStateFlow<String?>(null)
    val introduceMaxLengthCaption: StateFlow<String?>
        get() = _introduceMaxLengthCaption.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?>
        get() = _user

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    lateinit var eventObserver: EventObserver
    lateinit var uiStateObserver: UiStateObserver

    fun loadLoginUserInfo() {
        viewModelScope.launch {
            val loginUser = userRepository.getLoginUser()
            if (loginUser != null) {
                _user.emit(loginUser)
            } else {
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
            _user.first()?.updateThumbnailImg = bitmap
        }
    }

    fun updateUserBackgroundImg(bitmap: Bitmap?) {
        viewModelScope.launch {
            _user.first()?.updateProfileBackgroundImg = bitmap
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
            user.value?.let {
                if (userRepository.updateUser(it)) {
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
