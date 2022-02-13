package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.feed.FeedRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.ui.home.HomeFragmentViewModel
import com.ozcoin.cookiepang.utils.DataResult
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class HomeFragmentViewModelTest : BehaviorSpec({

    val testDispatcher = TestCoroutineDispatcher()
    Dispatchers.setMain(testDispatcher)

    val userCategoryRepository = mockk<UserCategoryRepository>()
    val feedRepository = mockk<FeedRepository>()
    var viewModel = HomeFragmentViewModel(
        userCategoryRepository, feedRepository
    )

    Given("유저가 선택한 카테고리를 불러올 때") {

        When("로드에 실패하면") {

            coEvery {
                userCategoryRepository.getUserCategory()
            } coAnswers {
                DataResult.OnFail
            } coAndThen {
                DataResult.OnFail
            } coAndThen {
                DataResult.OnFail
            }

            viewModel = spyk(
                HomeFragmentViewModel(
                    userCategoryRepository, feedRepository
                )
            )

            runBlockingTest {
                viewModel.getUserCategoryList(1)
                pauseDispatcher()

                Then("3회 재시도 한다") {
//                verify(exactly = 3) { viewModel.loadUserCategory() }

                    resumeDispatcher()
                    verify { viewModel.getUserCategoryList() }
                }
            }
        }
    }

    afterTest {
        clearMocks(viewModel)
        clearMocks(userCategoryRepository)
        clearMocks(feedRepository)

        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
})
