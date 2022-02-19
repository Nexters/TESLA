package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.selectcategory.SelectCategory
import com.ozcoin.cookiepang.domain.selectcategory.toUserCategory
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.domain.usercategory.toSelectCategory
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

    private fun navigateToCompleteUserReg() {
        navigateTo(SelectCategoryFragmentDirections.actionCompleteUserReg())
    }

    private suspend fun registUser(): Boolean {
        return if (registrationUser != null)
            userRepository.regUser(registrationUser!!)
        else
            false
    }

    private suspend fun isUserRegistration(): Boolean {
        return userRepository.getLoginUser() == null
    }

    suspend fun getCategoryList(): List<SelectCategory> {
        val result = userCategoryRepository.getAllUserCategory()
        val categories = if (result is DataResult.OnSuccess) {
            val categoryList = result.response.map { it.toSelectCategory() }.toList()
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
        list: List<SelectCategory>
    ): List<SelectCategory> {
        val result = userCategoryRepository.getUserCategory(user.userId)
        if (result is DataResult.OnSuccess) {
            val userList = result.response.map { it.toSelectCategory() }
            list.forEach { all ->
                val find = userList.find { it.categoryName == all.categoryName }
                if (find != null)
                    all.isSelected = true
            }
        }

        selectedCategories = list.map { it.toUserCategory() }
        return list
    }

    private suspend fun setUserInterestIn(user: User): Boolean {
        var result = false
        if (checkUserSelectedCategories()) {
            result = selectedCategories?.let { userCategoryRepository.setUserInterestIn(user, it) }
                ?: false
        }

        return result
    }

    fun clickNext() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            if (isUserRegistration()) {
                if (setUserInterestIn(registrationUser!!)) {
                    if (registUser()) {
                        uiStateObserver.update(UiState.OnSuccess)
                        navigateToCompleteUserReg()
                    } else {
                        uiStateObserver.update(UiState.OnFail)
                    }
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
