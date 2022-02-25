package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCategoryFragmentViewModel @Inject constructor(
    private val userCategoryRepository: UserCategoryRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    lateinit var uiStateObserver: UiStateObserver

    var selectedCategories: List<UserCategory>? = null
    var registrationUser: User? = null

    private fun checkUserSelectedCategories(): Boolean {
        return selectedCategories != null && selectedCategories!!.size >= 3
    }

    private fun navigateToOnBoarding() {
        navigateTo(SelectCategoryFragmentDirections.actionOnBoarding01())
    }

    private suspend fun isUserRegistration(): Boolean {
        return userRepository.getLoginUser() == null
    }

    private var isUserCategoryResetRequest: Boolean = false

    fun setRequestUserCategoryReset(reset: Boolean) {
        isUserCategoryResetRequest = reset
    }

    suspend fun getCategoryList(): List<UserCategory> {
        val result = userCategoryRepository.getAllUserCategory()
        val categories = if (result is DataResult.OnSuccess) {
            val categoryList = result.response
            if (isUserRegistration())
                categoryList
            else
                matchSelectedUserCategory(userRepository.getLoginUser()!!, categoryList)
        } else {
            emptyList()
        }
        return categories
    }

    private suspend fun matchSelectedUserCategory(
        user: User,
        list: List<UserCategory>
    ): List<UserCategory> {
        val result = userCategoryRepository.getUserCategory(user.userId)
        val selectedCategory = mutableListOf<UserCategory>()
        if (result is DataResult.OnSuccess) {
            val userList = result.response
            list.forEach { category ->
                val find = userList.find { it.categoryName == category.categoryName }
                if (find != null) {
                    category.isSelected = true
                    selectedCategory.add(category)
                }
            }
        }

        selectedCategories = selectedCategory.toList()
        return list
    }

    private suspend fun setUserInterestIn(user: User): Boolean {
        var result = false
        if (checkUserSelectedCategories()) {
            result = selectedCategories?.let {
                userCategoryRepository.setUserInterestIn(user, it)
            } ?: false
        }

        return result
    }

    fun clickNext() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            if (isUserCategoryResetRequest) {
                if (setUserInterestIn(registrationUser!!)) {
                    uiStateObserver.update(UiState.OnSuccess)
                    navigateToOnBoarding()
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            } else {
                if (setUserInterestIn(userRepository.getLoginUser()!!)) {
                    uiStateObserver.update(UiState.OnSuccess)
                    navigateUp(SelectCategoryFragment.KEY_RESET_USER_CATEGORY, true)
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }
}
