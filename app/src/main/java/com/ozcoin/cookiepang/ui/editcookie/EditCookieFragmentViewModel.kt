package com.ozcoin.cookiepang.ui.editcookie

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.editcookie.EditCookieRepository
import com.ozcoin.cookiepang.domain.klip.KlipContractTxRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.DataResult
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
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditCookieFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userCategoryRepository: UserCategoryRepository,
    private val editCookieRepository: EditCookieRepository,
    private val klipContractTxRepository: KlipContractTxRepository
) : BaseViewModel(), LifecycleEventObserver {

    companion object {
        private const val KLIP_PENDING_TYPE_MAKE = 910
        private const val KLIP_PENDING_TYPE_EDIT = 911
    }

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

    private val _editCookie = MutableStateFlow(EditCookie())
    val editCookie: StateFlow<EditCookie>
        get() = _editCookie

    val titleClickListener = TitleClickListener(
        EventObserver {
            showCloseEditingCookieDialog()
        }
    )

    private var klipPendingType = -1

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

    fun setEditCookie(editCookie: EditCookie) {
        viewModelScope.launch {
            _editCookie.emit(editCookie)
        }
    }

    fun getUserCategoryList() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            val result = userCategoryRepository.getAllUserCategory()
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
        uiStateObserver.update(UiState.OnLoading)

        if (isEssentialCookieInfoComplete(editCookie)) {
            viewModelScope.launch {
                if (klipContractTxRepository.requestMakeACookie(editCookie)) {
                    klipPendingType = KLIP_PENDING_TYPE_MAKE
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        } else {
            Timber.d("make a cookie fail(caused: isEssentialCookieInfoComplete false)")
            uiStateObserver.update(UiState.OnFail)
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
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                if (klipContractTxRepository.requestMakeACookie(editCookie)) {
                    klipPendingType = KLIP_PENDING_TYPE_EDIT
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        } else {
            Timber.d("edit cookie info fail(caused: isEssentialCookieInfoComplete false)")
        }
    }

    fun clickEditCookie() {
        editCookie.value.let {
            if (it.isEditPricingInfo) {
                editCookieInfo(it)
            } else {
                makeACookie(it)
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            klipContractTxRepository.getResult { result, tx_hash ->
                if (result && tx_hash != null) {
                    when (klipPendingType) {
                        KLIP_PENDING_TYPE_MAKE -> handleMakeACookieResult(tx_hash)
                        KLIP_PENDING_TYPE_EDIT -> handleEditCookiePriceResult(tx_hash)
                    }
                    klipPendingType = -1
                } else {
                    Timber.d("requestMakeACookie result($result, tx_hash=$tx_hash)")
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }

    private fun handleMakeACookieResult(tx_hash: String) {
        viewModelScope.launch {
            val resultCookieId = editCookieRepository.makeACookie(
                userRepository.getLoginUser()?.userId ?: "",
                tx_hash,
                editCookie.value
            )
            if (resultCookieId.isNotBlank()) {
                uiStateObserver.update(UiState.OnSuccess)
                showMakeACookieSuccessDialog(resultCookieId)
            } else {
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    private fun handleEditCookiePriceResult(tx_hash: String) {
        viewModelScope.launch {
            val loginUserId = userRepository.getLoginUser()?.userId ?: ""
            if (editCookieRepository.editCookieInfo(loginUserId, editCookie.value)) {
                navigateUp()
                uiStateObserver.update(UiState.OnSuccess)
            } else {
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }
}
