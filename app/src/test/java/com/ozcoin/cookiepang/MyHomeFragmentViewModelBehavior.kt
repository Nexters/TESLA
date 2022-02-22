package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.question.QuestionRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.userinfo.UserInfoRepository
import com.ozcoin.cookiepang.ui.myhome.MyHomeFragmentViewModel
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class MyHomeFragmentViewModelBehavior : BehaviorSpec() {
    init {
        val userInfoRepository = mockk<UserInfoRepository>()
        val userRepository = mockk<UserRepository>()
        val cookieRepository = mockk<CookieRepository>()
        val questionRepository = mockk<QuestionRepository>()
        val viewModel = spyk(
            MyHomeFragmentViewModel(
                userRepository, userInfoRepository, cookieRepository, questionRepository
            )
        )

        var testDispatcher: TestCoroutineDispatcher? = null
        val coroutineTestRule: CoroutineTestRule by CoroutineTestRuleImpl()

        var userId = ""

        beforeTest {
            testDispatcher = coroutineTestRule.beforeTest()
        }

        afterTest {
            coroutineTestRule.afterTest(testDispatcher!!)
            MockUtil.clearMocks(
                listOf(
                    userInfoRepository, userRepository, viewModel, cookieRepository, questionRepository
                )
            )
        }

        var uiState: UiState? = null
        viewModel.uiStateObserver = UiStateObserver { uiState = it }

        Given("유저 아이디") {

            When("정상 문자열이 아니면") {

                userId = ""
                userId.shouldBeEmpty()

                And("로그인된 유저의 정보가 없음") {

                    coEvery {
                        userRepository.getLoginUser()
                    } coAnswers {
                        null
                    }

                    Then("이전 화면으로 이동") {
                        testDispatcher?.runBlockingTest {
                            pauseDispatcher()
                            viewModel.loadUserInfo(userId)

                            resumeDispatcher()

                            viewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
                        }
                    }
                }

                And("로그인된 유저의 아이디 정보가 존재") {

                    val loginUser = DummyUtil.getLoginUser()

                    coEvery {
                        userRepository.getLoginUser()
                    } coAnswers {
                        loginUser
                    }

                    coEvery {
                        userInfoRepository.getUserInfo(loginUser.userId)
                    } coAnswers {
                        DummyUtil.getUserInfo(false)
                    }

                    coEvery { cookieRepository.getCreatedCookieList(loginUser.userId) } coAnswers { DummyUtil.getCreatedCookieList() }
                    coEvery { cookieRepository.getCollectedCookieList(loginUser.userId) } coAnswers { DummyUtil.getCollectedCookieList() }
                    coEvery { questionRepository.getQuestionList(loginUser.userId) } coAnswers { DummyUtil.getQuestionList() }

                    Then("로그인된 유저의 아이디로 유저 정보 로드") {
                        testDispatcher?.runBlockingTest {
                            pauseDispatcher()
                            viewModel.loadUserInfo(userId)

                            resumeDispatcher()

                            verify(exactly = 1) { viewModel.loadUserInfo(loginUser.userId) }
                            uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                        }
                    }
                }
            }

            When("정상 문자열이면") {

                userId = "user"
                val userInfo = DummyUtil.getUserInfo(false)
                coEvery {
                    userInfoRepository.getUserInfo(userId)
                } coAnswers {
                    userInfo
                }

                coEvery { cookieRepository.getCreatedCookieList(userId) } coAnswers { DummyUtil.getCreatedCookieList() }
                coEvery { cookieRepository.getCollectedCookieList(userId) } coAnswers { DummyUtil.getCollectedCookieList() }
                coEvery { questionRepository.getQuestionList(userId) } coAnswers { DummyUtil.getQuestionList() }

                Then("유저 정보 로드 시도") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.loadUserInfo(userId)
                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()

                        uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                        viewModel.user.first() shouldBe (userInfo as DataResult.OnSuccess).response
                    }
                }
            }
        }

        Given("유저 정보") {

            When("로드에 실패") {

                coEvery {
                    userInfoRepository.getUserInfo(userId)
                } coAnswers {
                    DataResult.OnFail
                }

                Then("이전 화면으로 이동") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.loadUserInfo(userId)
                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()

                        uiState.shouldBeInstanceOf<UiState.OnFail>()
                        viewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
                    }
                }
            }

            When("로드에 성공") {

                val userInfo = DummyUtil.getUserInfo(false)
                coEvery {
                    userInfoRepository.getUserInfo(userId)
                } coAnswers {
                    userInfo
                }

                coEvery { cookieRepository.getCreatedCookieList(userId) } coAnswers { DummyUtil.getCreatedCookieList() }
                coEvery { cookieRepository.getCollectedCookieList(userId) } coAnswers { DummyUtil.getCollectedCookieList() }
                coEvery { questionRepository.getQuestionList(userId) } coAnswers { DummyUtil.getQuestionList() }

                Then("수집/만든 쿠키, 질문 리스트 로드 시도") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.loadUserInfo(userId)
                        uiState.shouldBeInstanceOf<UiState.OnLoading>()

                        resumeDispatcher()

                        uiState.shouldBeInstanceOf<UiState.OnSuccess>()
                    }
                }
            }
        }
    }
}
