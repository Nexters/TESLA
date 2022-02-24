package com.ozcoin.cookiepang.ui.ask

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.ask.Ask
import com.ozcoin.cookiepang.domain.ask.AskRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.TextInputUtil
import com.ozcoin.cookiepang.utils.TitleClickListener
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AskFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository,
    private val userRepository: UserRepository,
    private val askRepository: AskRepository
) : BaseViewModel() {

    private val _userCategoryList = MutableStateFlow<List<UserCategory>>(emptyList())
    val userCategoryList: StateFlow<List<UserCategory>>
        get() = _userCategoryList.asStateFlow()

    private val _questionMaxLengthCaption = MutableStateFlow<String?>(null)
    val questionMaxLengthCaption: StateFlow<String?>
        get() = _questionMaxLengthCaption.asStateFlow()

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    lateinit var uiStateObserver: UiStateObserver

    var ask: Ask? = null
        private set

    fun emitQuestionLength(length: Int) {
        val caption = if (length >= 20)
            TextInputUtil.getMaxLengthFormattedString(length, 25)
        else
            null

        viewModelScope.launch {
            _questionMaxLengthCaption.emit(caption)
        }
    }

    fun getUserCategoryList(receiverUserId: String) {
        viewModelScope.launch {
            val result = userCategoryRepository.getAllUserCategory()
            if (result is DataResult.OnSuccess) {
                _userCategoryList.emit(result.response)
                val senderUserId = userRepository.getLoginUser()?.userId ?: ""
                ask = Ask(
                    senderUserId = senderUserId,
                    receiverUserId = receiverUserId,
                    question = ""
                )
            }
        }
    }

    private fun sendAsk() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            ask?.let {
                if (askRepository.askToUser(it)) {
                    uiStateObserver.update(UiState.OnSuccess)
                    navigateUp()
                } else
                    uiStateObserver.update(UiState.OnFail)
            } ?: uiStateObserver.update(UiState.OnFail)
        }
    }

    fun selectCategory(userCategory: UserCategory) {
        ask?.selectedCategory = userCategory
    }

    fun clickMakeACookie() {
        sendAsk()
    }
}
