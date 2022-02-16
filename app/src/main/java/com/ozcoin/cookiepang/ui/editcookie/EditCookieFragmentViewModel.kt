package com.ozcoin.cookiepang.ui.editcookie

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditCookieFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository
) : BaseViewModel() {
    private val _userCategoryList = MutableStateFlow<List<UserCategory>>(emptyList())
    val userCategoryList: StateFlow<List<UserCategory>>
        get() = _userCategoryList.asStateFlow()

    fun getUserCategoryList() {
        viewModelScope.launch {
            val result = userCategoryRepository.getUserCategory()
            if (result is DataResult.OnSuccess) {
                Timber.d("getUserCategoryList onSuccess")
                _userCategoryList.emit(result.response)
            } else {
                Timber.d("getUserCategoryList onFail")

//                    sendUiState?.invoke(UiState.OnFail)
            }
        }
    }
}
