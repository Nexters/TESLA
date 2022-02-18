package com.ozcoin.cookiepang.ui.myhome

import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.userinfo.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyHomeFragmentViewModel @Inject constructor() : BaseViewModel() {

    private val _questionList = MutableStateFlow<List<Question>>(emptyList())
    val questionList: StateFlow<List<Question>>
        get() = _questionList

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?>
        get() = _userInfo

    fun loadUserInfo(userId: String?) {
    }

    fun clickAskMe() {
    }

    fun clickEditProfile() {
    }
}
