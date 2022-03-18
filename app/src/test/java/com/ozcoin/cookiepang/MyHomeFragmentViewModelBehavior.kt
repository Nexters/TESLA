package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.question.QuestionRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.ui.myhome.MyHomeFragmentViewModel
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import com.ozcoin.cookiepang.utils.Event
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class MyHomeFragmentViewModelBehavior : BehaviorSpec() {
    init {
        val userRepository = mockk<UserRepository>()
        val cookieRepository = mockk<CookieRepository>()
        val questionRepository = mockk<QuestionRepository>()
        val viewModel = spyk(
            MyHomeFragmentViewModel(
                userRepository, cookieRepository, questionRepository
            )
        )

        var testDispatcher: TestCoroutineDispatcher? = null
        val coroutineTestRule: CoroutineTestRule by CoroutineTestRuleImpl()

        var userId: String

        beforeTest {
            testDispatcher = coroutineTestRule.beforeTest()
        }

        afterTest {
            coroutineTestRule.afterTest(testDispatcher!!)
            MockUtil.clearMocks(
                listOf(
                    userRepository, viewModel, cookieRepository, questionRepository
                )
            )
        }

        Given("조회 하려는 유저 아이디 정보가") {

            When("정상 문자열이 아니고") {

                userId = ""
                userId.shouldBeEmpty()

                And("로그인된 유저의 정보가 없으면") {

                    coEvery {
                        userRepository.getLoginUser()
                    } coAnswers {
                        null
                    }

                    Then("이전 화면으로 이동한다.") {
                        testDispatcher?.runBlockingTest {
                            pauseDispatcher()
                            viewModel.loadUserInfo(userId)

                            resumeDispatcher()

                            viewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
                        }
                    }
                }

                And("로그인된 유저의 아이디 정보가 존재하면") {

                    val loginUser = DummyUtil.getLoginUser()

                    coEvery {
                        userRepository.getLoginUser()
                    } coAnswers {
                        loginUser
                    }

                    coEvery { cookieRepository.getCreatedCookieList(loginUser.userId) } coAnswers { DummyUtil.getCreatedCookieList() }
                    coEvery { cookieRepository.getCollectedCookieList(loginUser.userId) } coAnswers { DummyUtil.getCollectedCookieList() }
                    coEvery { questionRepository.getQuestionList(loginUser.userId) } coAnswers { DummyUtil.getQuestionList() }

                    Then("내 정보를 로드 한다.") {
                        viewModel.loadUserInfo(userId)

                        coVerify { userRepository.getLoginUser() }
                        coVerify { cookieRepository.getCreatedCookieList(loginUser.userId) }
                        coVerify { cookieRepository.getCollectedCookieList(loginUser.userId) }
                        coVerify { questionRepository.getQuestionList(loginUser.userId) }

                        viewModel.isMyPage.value shouldBe true
                    }
                }
            }

            When("정상 문자열인데") {

                userId = "10"
                userId.shouldNotBeEmpty()

                And("유저 정보를 가져오는데 실패하면") {

                    coEvery {
                        userRepository.getUser(userId)
                    } coAnswers {
                        DataResult.OnFail()
                    }

                    Then("이전 화면으로 이동한다.") {
                        viewModel.loadUserInfo(userId)
                        viewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
                    }
                }

                And("유저 정보를 가져오는데 성공하고") {

                    coEvery {
                        userRepository.getUser(userId)
                    } coAnswers {
                        DataResult.OnSuccess(DummyUtil.getLoginUser())
                    }

                    And("로그인 유저 정보를 가져왔는데 조회하려는 유저의 아이디와 같다면") {

                        val loginUser = DummyUtil.getLoginUser()
                        coEvery {
                            userRepository.getLoginUser()
                        } coAnswers {
                            loginUser
                        }

                        coEvery { cookieRepository.getCreatedCookieList(loginUser.userId) } coAnswers { DummyUtil.getCreatedCookieList() }
                        coEvery { cookieRepository.getCollectedCookieList(loginUser.userId) } coAnswers { DummyUtil.getCollectedCookieList() }
                        coEvery { questionRepository.getQuestionList(loginUser.userId) } coAnswers { DummyUtil.getQuestionList() }

                        Then("내 정보 화면이며, 로그인된 유저 데이터로 정보를 로드 한다.") {
                            viewModel.loadUserInfo(userId)

                            coVerify { userRepository.getLoginUser() }
                            coVerify { cookieRepository.getCreatedCookieList(loginUser.userId) }
                            coVerify { cookieRepository.getCollectedCookieList(loginUser.userId) }
                            coVerify { questionRepository.getQuestionList(loginUser.userId) }

                            viewModel.isMyPage.value shouldBe true
                        }
                    }

                    And("로그인 유저 정보를 가져왔는데 조회하려는 유저의 아이디와 다르다면") {
                        val user = DataResult.OnSuccess(DummyUtil.getLoginUser())
                        coEvery {
                            userRepository.getUser(userId)
                        } coAnswers {
                            user
                        }

                        val loginUser = DummyUtil.getLoginUser().apply { this.userId = "201" }
                        coEvery {
                            userRepository.getLoginUser()
                        } coAnswers {
                            loginUser
                        }

                        coEvery { cookieRepository.getCreatedCookieList(user.response.userId) } coAnswers { DummyUtil.getCreatedCookieList() }
                        coEvery { cookieRepository.getCollectedCookieList(user.response.userId) } coAnswers { DummyUtil.getCollectedCookieList() }
                        coEvery { questionRepository.getQuestionList(user.response.userId) } coAnswers { DummyUtil.getQuestionList() }

                        Then("내 정보 화면이 아니며, 요청 받은 유저의 정보를 로드한다.") {
                            viewModel.loadUserInfo(userId)

                            coVerify { userRepository.getLoginUser() }
                            coVerify { cookieRepository.getCreatedCookieList(user.response.userId) }
                            coVerify { cookieRepository.getCollectedCookieList(user.response.userId) }
                            coVerify { questionRepository.getQuestionList(user.response.userId) }

                            viewModel.isMyPage.value shouldBe false
                        }
                    }
                }
            }
        }
    }
}
