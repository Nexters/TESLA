package com.ozcoin.cookiepang.ui.myhome

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.cookie.Cookie
import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.question.QuestionRepository
import com.ozcoin.cookiepang.domain.question.toEditCookie
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.TitleClickListener
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyHomeFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cookieRepository: CookieRepository,
    private val questionRepository: QuestionRepository
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

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?>
        get() = _user

    private val _isMyPage = MutableStateFlow(true)
    val isMyPage: StateFlow<Boolean>
        get() = _isMyPage

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    lateinit var eventObserver: EventObserver
    lateinit var uiStateObserver: UiStateObserver

    private suspend fun loadUserInfo(user: User) =
        withContext(Dispatchers.IO) {
            val collectedCookie = async { loadCollectedCookie(user.userId) }
            val createdCookie = async { loadCreatedCookie(user.userId) }
            val question = async { loadQuestions(user.userId) }

            collectedCookie.await()
            createdCookie.await()
            question.await()
        }

    fun loadUserInfo(userId: String?) {
        viewModelScope.launch {
            var isMyPage = false
            val user = if (userId.isNullOrBlank()) {
                isMyPage = true
                userRepository.getLoginUser()
            } else {
                userRepository.getUser(userId).let {
                    if (it is DataResult.OnSuccess) it.response else null
                }
            }
            if (user != null) {
                _isMyPage.emit(isMyPage)
                _user.emit(user)
                loadUserInfo(user)
            } else {
                Timber.d("user is null, navigateUp()")
                navigateUp()
            }
        }
    }

    private suspend fun loadCollectedCookie(userId: String) {
        cookieRepository.getCollectedCookieList(userId).let {
            if (it is DataResult.OnSuccess)
                _collectedCookieList.emit(it.response)
        }
        Timber.d("loadCollectedCookie finish")
    }

    private suspend fun loadCreatedCookie(userId: String) {
        cookieRepository.getCreatedCookieList(userId).let {
            if (it is DataResult.OnSuccess)
                _createdCookieList.emit(it.response)
        }
        Timber.d("loadCreatedCookie finish")
    }

    private suspend fun loadQuestions(userId: String) {
        questionRepository.getQuestionList(userId).let {
            if (it is DataResult.OnSuccess)
                _questionList.emit(it.response)
        }
        Timber.d("loadQuestions finish")
    }

    fun acceptQuestion(question: Question) {
        eventObserver.update(Event.Nav.ToEditCookie(question.toEditCookie()))
    }

    fun ignoreQuestion(question: Question) {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            if (questionRepository.ignoreQuestion(question)) {
                Timber.d("ignoreQuestion success, so reload questions")
                _user.first()?.userId?.let {
                    loadQuestions(it)
                } ?: Timber.d("userInfo is not valid")
            }
            uiStateObserver.update(UiState.OnSuccess)
        }
    }

    fun navigateToCookieDetail(cookieId: String) {
        navigateTo(MyHomeFragmentDirections.actionCookieDetail(cookieId))
    }

    private fun navigateToEditProfile() {
        navigateTo(MyHomeFragmentDirections.actionEditProfile())
    }

    private fun navigateToAsk() {
        _user.value?.userId?.let { navigateTo(MyHomeFragmentDirections.actionAsk(it)) }
    }

    fun clickAskMe() {
        navigateToAsk()
    }

    fun clickEditProfile() {
        navigateToEditProfile()
    }
}
