package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.ui.registuser.SelectCategoryFragmentViewModel
import com.ozcoin.cookiepang.utils.DummyUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class SelectCategoryFragmentViewModelBehavior : BehaviorSpec() {
    init {

        val userCategoryRepository = mockk<UserCategoryRepository>()
        val userRepository = mockk<UserRepository>()
        val selectCategoryFragmentViewModel = spyk(
            SelectCategoryFragmentViewModel(
                userCategoryRepository, userRepository
            )
        )

        var testDispatcher: TestCoroutineDispatcher? = null
        val coroutineTestRule: CoroutineTestRule by CoroutineTestRuleImpl()

        var loginUser: User? = null

        beforeTest {
            testDispatcher = coroutineTestRule.beforeTest()

            coEvery {
                userRepository.getLoginUser()
            } coAnswers {
                loginUser
            }
        }

        afterTest {
            coroutineTestRule.afterTest(testDispatcher!!)
            MockUtil.clearMocks(
                listOf(
                    userCategoryRepository, userRepository, selectCategoryFragmentViewModel
                )
            )
        }

        val registrationUser = User()
        var uiState: UiState? = null
        selectCategoryFragmentViewModel.uiStateObserver = UiStateObserver {
            uiState = it
        }

        Given("회원가입 하려는 유저 정보 존재") {

            selectCategoryFragmentViewModel.registrationUser = registrationUser

            When("카테고리 3개 미만 선택") {

                selectCategoryFragmentViewModel.selectedCategories = emptyList()

                Then("회원 가입 실패") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        selectCategoryFragmentViewModel.clickNext()

                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()
                        uiState.shouldBeInstanceOf<UiState.OnFail>()
                    }
                }
            }

            When("카테고리 3개 이상 선택") {

                val selectedCategories = DummyUtil.getUserCategoryList().response
                selectCategoryFragmentViewModel.selectedCategories = selectedCategories

                And("카테고리 등록 성공") {

                    coEvery {
                        userCategoryRepository.setUserInterestIn(
                            registrationUser,
                            selectedCategories
                        )
                    } coAnswers {
                        true
                    }

                    And("유저 등록 실패") {

                        coEvery {
                            userRepository.regUser(registrationUser)
                        } coAnswers {
                            false
                        }

                        Then("회원 가입 실패") {
                            testDispatcher?.runBlockingTest {
                                pauseDispatcher()
                                selectCategoryFragmentViewModel.clickNext()

                                uiState.shouldBeInstanceOf<UiState.OnLoading>()

                                resumeDispatcher()
                                uiState.shouldBeInstanceOf<UiState.OnFail>()
                            }
                        }
                    }

                    coEvery {
                        userCategoryRepository.setUserInterestIn(
                            registrationUser,
                            selectedCategories
                        )
                    } coAnswers {
                        true
                    }

                    And("유저 등록 성공") {

                        coEvery {
                            userRepository.regUser(registrationUser)
                        } coAnswers {
                            true
                        }

                        Then("다음 화면으로 진행") {
                            testDispatcher?.runBlockingTest {
                                pauseDispatcher()
                                selectCategoryFragmentViewModel.clickNext()

                                uiState.shouldBeInstanceOf<UiState.OnLoading>()

                                resumeDispatcher()
                                uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                                selectCategoryFragmentViewModel.eventFlow.first()
                                    .shouldBeInstanceOf<Event.Nav.To>()
                            }
                        }
                    }
                }

                And("카테고리 등록 실패") {

                    coEvery {
                        userCategoryRepository.setUserInterestIn(
                            registrationUser,
                            selectedCategories
                        )
                    } coAnswers {
                        false
                    }

                    Then("회원 가입 실패") {
                        testDispatcher?.runBlockingTest {
                            pauseDispatcher()
                            selectCategoryFragmentViewModel.clickNext()

                            uiState.shouldBeInstanceOf<UiState.OnLoading>()

                            resumeDispatcher()
                            uiState.shouldBeInstanceOf<UiState.OnFail>()
                        }
                    }
                }
            }
        }

        Given("등록한 유저 정보 존재") {

            loginUser = User()

            When("카테고리 3개 미만 선택") {
                val selectedCategories = emptyList<UserCategory>()
                selectCategoryFragmentViewModel.selectedCategories = selectedCategories

                coEvery {
                    userCategoryRepository.setUserInterestIn(loginUser!!, selectedCategories)
                } coAnswers {
                    false
                }

                Then("관심 카테고리 등록 실패") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        selectCategoryFragmentViewModel.clickNext()

                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()
                        uiState.shouldBeInstanceOf<UiState.OnFail>()
                    }
                }
            }

            When("카테고리 3개 이상 선택") {

                val selectedCategories = DummyUtil.getUserCategoryList().response
                selectCategoryFragmentViewModel.selectedCategories = selectedCategories

                coEvery {
                    userCategoryRepository.setUserInterestIn(loginUser!!, selectedCategories)
                } coAnswers {
                    true
                }

                Then("관심 카테고리 등록 성공하여 이전 화면으로 이동") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        selectCategoryFragmentViewModel.clickNext()

                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()
                        uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                        selectCategoryFragmentViewModel.eventFlow.first()
                            .shouldBeInstanceOf<Event.Nav.Up>()
                    }
                }
            }
        }
    }
}
