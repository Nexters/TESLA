package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.ui.registuser.SelectCategoryFragmentViewModel
import com.ozcoin.cookiepang.utils.DummyUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
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

        beforeTest {
            testDispatcher = coroutineTestRule.beforeTest()
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
        selectCategoryFragmentViewModel.uiStateObserver = UiStateObserver { uiState = it }

        Given("다음 버튼 눌렀을 때") {

            When("사용자의 관심 카테고리 리셋 요청이 존재하지 않고") {

                selectCategoryFragmentViewModel.registrationUser = registrationUser
                selectCategoryFragmentViewModel.setRequestUserCategoryReset(false)

                And("선택된 카테고리가 3개 미만이라면") {

                    selectCategoryFragmentViewModel.selectedCategories = emptyList()

                    Then("관심 카테고리 등록에 실패한다.") {
                        testDispatcher?.runBlockingTest {
                            pauseDispatcher()
                            selectCategoryFragmentViewModel.clickNext()
                            uiState.shouldBeInstanceOf<UiState.OnLoading>()

                            resumeDispatcher()
                            uiState.shouldBeInstanceOf<UiState.OnFail>()
                        }
                    }
                }

                And("선택된 카테고리가 3개 이상이고") {

                    val selectedCategories = DummyUtil.getUserCategoryList().response
                    selectCategoryFragmentViewModel.selectedCategories = selectedCategories

                    And("관심 카테고리 등록에 성공한다면") {

                        coEvery {
                            userCategoryRepository.setUserInterestIn(
                                registrationUser,
                                selectedCategories
                            )
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

                    And("관심 카테고리 등록에 실패한다면") {

                        coEvery {
                            userCategoryRepository.setUserInterestIn(
                                registrationUser,
                                selectedCategories
                            )
                        } coAnswers {
                            false
                        }

                        Then("관심 카테고리 등록에 실패한다.") {
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

            When("사용자의 관심 카테고리 리셋 요청이 존재하고") {
                val loginUser = DummyUtil.getLoginUser()

                selectCategoryFragmentViewModel.setRequestUserCategoryReset(true)

                And("선택된 카테고리가 3개 미만이라면") {

                    coEvery {
                        userRepository.getLoginUser()
                    } coAnswers {
                        loginUser
                    }

                    selectCategoryFragmentViewModel.selectedCategories = emptyList()

                    Then("관심 카테고리 등록에 실패한다.") {
                        testDispatcher?.runBlockingTest {
                            pauseDispatcher()
                            selectCategoryFragmentViewModel.clickNext()
                            uiState.shouldBeInstanceOf<UiState.OnLoading>()

                            resumeDispatcher()
                            uiState.shouldBeInstanceOf<UiState.OnFail>()
                        }
                    }
                }

                And("선택된 카테고리가 3개 이상이고") {
                    val selectedCategories = DummyUtil.getUserCategoryList().response
                    selectCategoryFragmentViewModel.selectedCategories = selectedCategories

                    And("관심 카테고리 등록에 성공한다면") {

                        coEvery {
                            userRepository.getLoginUser()
                        } coAnswers {
                            loginUser
                        }

                        coEvery {
                            userCategoryRepository.setUserInterestIn(
                                loginUser,
                                selectedCategories
                            )
                        } coAnswers {
                            true
                        }

                        Then("결과 성공으로 이전 화면으로 이동한다.") {
                            testDispatcher?.runBlockingTest {
                                pauseDispatcher()
                                selectCategoryFragmentViewModel.clickNext()

                                uiState.shouldBeInstanceOf<UiState.OnLoading>()

                                resumeDispatcher()
                                uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                                (selectCategoryFragmentViewModel.eventFlow.first() as Event.Nav.Up).value shouldBe true
                            }
                        }
                    }

                    And("관심 카테고리 등록에 실패한다면") {

                        coEvery {
                            userRepository.getLoginUser()
                        } coAnswers {
                            loginUser
                        }

                        coEvery {
                            userCategoryRepository.setUserInterestIn(
                                loginUser,
                                selectedCategories
                            )
                        } coAnswers {
                            false
                        }

                        Then("관심 카테고리 등록에 실패한다.") {
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
        }
    }
}
