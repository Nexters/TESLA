package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.userinfo.UserInfoRepository
import com.ozcoin.cookiepang.ui.editprofile.EditProfileFragmentViewModel
import com.ozcoin.cookiepang.utils.DataResult
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
class EditProfileViewModelBehavior : BehaviorSpec() {
    init {
        val userInfoRepository = mockk<UserInfoRepository>()
        val userRepository = mockk<UserRepository>()
        val viewModel = spyk(
            EditProfileFragmentViewModel(
                userRepository, userInfoRepository
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
                    userInfoRepository, userRepository, viewModel
                )
            )
        }

        var uiState: UiState? = null
        viewModel.uiStateObserver = UiStateObserver { uiState = it }

        Given("로그인된 유저 아이디 정보") {

            When("정상 문자열이 아니면") {

                coEvery {
                    userRepository.getLoginUser()
                } coAnswers {
                    null
                }

                Then("이전 화면으로 이동") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.loadLoginUserInfo()
                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()

                        uiState.shouldBeInstanceOf<UiState.OnFail>()
                        viewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
                    }
                }
            }

            When("정상 문자열이면") {

                val loginUser = DummyUtil.getLoginUser()
                val userInfo = DummyUtil.getUserInfo(false)

                coEvery {
                    userRepository.getLoginUser()
                } coAnswers {
                    loginUser
                }

                coEvery {
                    userInfoRepository.getUserInfo(loginUser.userId)
                } coAnswers {
                    userInfo
                }

                Then("유저 정보 로드") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.loadLoginUserInfo()
                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()

                        uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                        viewModel.userInfo.first() shouldBe (userInfo as DataResult.OnSuccess).response
                    }
                }
            }
        }

        Given("업데이트하려는 유저 정보 존재") {
            val userInfo = (DummyUtil.getUserInfo(true) as DataResult.OnSuccess).response

            When("저장 버튼 클릭하면") {

                coEvery { viewModel.userInfo.value } coAnswers { userInfo }

                coEvery {
                    userInfoRepository.updateUserInfo(userInfo)
                } coAnswers {
                    true
                }

                Then("유저 정보 업데이트 성공 후 이전 화면 이동") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.clickSaveUserInfo()
                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()

                        uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                        viewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
                    }
                }
            }
        }
    }
}
