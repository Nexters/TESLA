package com.ozcoin.cookiepang.ui.editcookie

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.editcookie.EditCookieRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
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
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditCookieFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository,
    private val editCookieRepository: EditCookieRepository
) : BaseViewModel() {

    private val _editCookieEventFlow = MutableEventFlow<EditCookieEvent>()
    val editCookieEventFlow: EventFlow<EditCookieEvent>
        get() = _editCookieEventFlow.asEventFlow()

    private val _userCategoryList = MutableStateFlow<List<UserCategory>>(emptyList())
    val userCategoryList: StateFlow<List<UserCategory>>
        get() = _userCategoryList.asStateFlow()

    private val _questionMaxLengthCaption = MutableStateFlow<String?>(null)
    val questionMaxLengthCaption: StateFlow<String?>
        get() = _questionMaxLengthCaption.asStateFlow()

    private val _answerMaxLengthCaption = MutableStateFlow<String?>(null)
    val answerMaxLengthCaption: StateFlow<String?>
        get() = _answerMaxLengthCaption.asStateFlow()

    lateinit var uiStateObserver: UiStateObserver
    lateinit var eventObserver: EventObserver

    fun emitQuestionLength(length: Int) {
        val caption = if (length >= 20)
            TextInputUtil.getMaxLengthFormattedString(length, 25)
        else
            null

        viewModelScope.launch {
            _questionMaxLengthCaption.emit(caption)
        }
    }

    fun emitAnswerLength(length: Int) {
        val caption = if (length >= 45)
            TextInputUtil.getMaxLengthFormattedString(length, 50)
        else
            null

        viewModelScope.launch {
            _answerMaxLengthCaption.emit(caption)
        }
    }

    fun getUserCategoryList() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            val result = userCategoryRepository.getUserCategory()
            if (result is DataResult.OnSuccess) {
                Timber.d("getUserCategoryList onSuccess")
                _userCategoryList.emit(result.response)
                uiStateObserver.update(UiState.OnSuccess)
            } else {
                Timber.d("getUserCategoryList onFail")
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    fun showCloseEditingCookieDialog() {
        val event = Event.ShowDialog(
            DialogUtil.getCloseEditingCookie(),
            callback = {
                Timber.d("CloseEditingCookieDialog result($it)")
                if (it) navigateUp()
            }
        )

        viewModelScope.launch {
            eventObserver.update(event)
        }
    }

    private fun sendEditCookieEvent(event: EditCookieEvent) {
        viewModelScope.launch {
            _editCookieEventFlow.emit(event)
        }
    }

    private fun getEditCookieInfoResult() {
        viewModelScope.launch {
            editCookieRepository.getResult()
        }
    }

    private fun getMakeACookieResult() {
        viewModelScope.launch {
            if (editCookieRepository.getResult()) {
                showMakeACookieSuccessDialog("haha")
            }
        }
    }

    private fun showMakeACookieSuccessDialog(cookieId: String) {
        eventObserver.update(
            Event.ShowDialog(
                DialogUtil.getMakeCookieSuccess(),
                callback = {
                    if (it) {
                        navigateTo(EditCookieFragmentDirections.actionCookieDetail(cookieId))
                    }
                }
            )
        )
    }

    private fun makeACookie(editCookie: EditCookie) {
        if (isEssentialCookieInfoComplete(editCookie)) {
            viewModelScope.launch {
                editCookieRepository.makeACookie(editCookie)

                getMakeACookieResult()
            }
        } else {
            Timber.d("make a cookie fail(caused: isEssentialCookieInfoComplete false)")
        }
    }

    private fun isEssentialCookieInfoComplete(editCookie: EditCookie): Boolean {
        val event = editCookie.run {
            if (question.isEmpty()) {
                Timber.d("Question info missing")
                EditCookieEvent.QuestionInfoMissing
            } else if (answer.isEmpty()) {
                Timber.d("Answer info missing")
                EditCookieEvent.AnswerInfoMissing
            } else if (hammerCost.isEmpty() || hammerCost.toInt() <= 0) {
                Timber.d("Hammer Cost info missing")
                EditCookieEvent.HammerCostInfoMissing
            } else if (selectedCategory == null) {
                Timber.d("Selected Category info missing")
                EditCookieEvent.CategoryInfoMissing
            } else {
                null
            }
        }

        if (event != null)
            sendEditCookieEvent(event)

        return event == null
    }

    private fun editCookieInfo(editCookie: EditCookie) {
        if (isEssentialCookieInfoComplete(editCookie)) {
            viewModelScope.launch {
                editCookieRepository.editCookieInfo(editCookie)
            }
        } else {
            Timber.d("edit cookie info fail(caused: isEssentialCookieInfoComplete false)")
        }
    }

    fun clickEditCookie(editCookie: EditCookie) {
        if (editCookie.isEditPricingInfo) {
            editCookieInfo(editCookie)
        } else {
            makeACookie(editCookie)
        }
    }
}
