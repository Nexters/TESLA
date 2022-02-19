package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.feed.FeedRepository
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.ui.home.HomeFragmentViewModel
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class HomeFragmentViewModelBehavior : BehaviorSpec() {
    init {

        var testDispatcher: TestCoroutineDispatcher? = null
        val coroutineTestRule: CoroutineTestRule by CoroutineTestRuleImpl()

        val userCategoryRepository = mockk<UserCategoryRepository>()
        val feedRepository = mockk<FeedRepository>()
        val userRepository = mockk<UserRepository>()
        val viewModel = spyk(
            HomeFragmentViewModel(
                userCategoryRepository, userRepository, feedRepository
            )
        )
        val user = User()

        var uiState: UiState? = null
        val uiStateObserver = UiStateObserver {
            uiState = it
        }

        viewModel.uiStateObserver = uiStateObserver

        beforeTest {
            testDispatcher = coroutineTestRule.beforeTest()

            coEvery {
                userRepository.getLoginUser()
            } coAnswers {
                user
            }
        }

        afterTest {
            MockUtil.clearMocks(listOf(viewModel, userCategoryRepository, feedRepository))
            coroutineTestRule.afterTest(testDispatcher!!)
        }

        Given("유저가 카테고리 데이터 로드") {

            When("로드에 실패하면") {

                coEvery {
                    userCategoryRepository.getUserCategory(user.userId)
                } coAnswers {
                    DataResult.OnFail
                } coAndThen {
                    DataResult.OnFail
                } coAndThen {
                    DataResult.OnFail
                }

                Then("재시도 한다") {
                    testDispatcher?.runBlockingTest {
                        pauseDispatcher()
                        viewModel.getUserCategoryList()
                        uiState shouldBe UiState.OnLoading

                        resumeDispatcher()

                        verify { viewModel.getUserCategoryList() }
                        uiState shouldBe UiState.OnFail
                    }
                }
            }

            When("로드에 성공하면") {

                coEvery {
                    feedRepository.getFeedList(UserCategory.typeAll())
                } coAnswers {
                    DummyUtil.getFeedList()
                }

                coEvery {
                    userCategoryRepository.getUserCategory(user.userId)
                } coAnswers {
                    DummyUtil.getUserCategoryList()
                }

                Then("All 카테고리로 피드 리스트를 가져온다") {

                    viewModel.userCategoryList.takeWhile {
                        it.isNotEmpty()
                    }.take(1).let {
                        viewModel.getFeedList(UserCategory.typeAll())
                    }

                    testDispatcher?.runBlockingTest {

                        pauseDispatcher()
                        viewModel.getUserCategoryList()

                        uiState shouldBe UiState.OnLoading

                        resumeDispatcher()

                        val list = viewModel.userCategoryList.first()
                        list.size shouldNotBe 0

                        verify { viewModel.getFeedList(UserCategory.typeAll()) }

                        uiState shouldBe UiState.OnSuccess
                        viewModel.feedList.first().size shouldNotBe 0
                    }
                }
            }
        }
    }
}
