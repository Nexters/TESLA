package com.ozcoin.cookiepang.ui.home

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository
) : BaseViewModel() {

    private val _userCategoryList = MutableStateFlow<List<UserCategory>>(emptyList())
    val userCategoryList: StateFlow<List<UserCategory>>
        get() = _userCategoryList.asStateFlow()

    var sendUiState: ((UiState) -> Unit)? = null

    fun loadUserCategory() {
        viewModelScope.launch {
            sendUiState?.invoke(UiState.OnLoading)
            _userCategoryList.emit(
                userCategoryRepository.getUserCategory()
            )
        }
    }
}
