package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import com.ozcoin.cookiepang.ui.home.HomeFragmentViewModel
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain

@ExperimentalCoroutinesApi
class HomeFragmentViewModelTest : BehaviorSpec({

    val testDispatcher = TestCoroutineDispatcher()

    val userCategoryRepository = mockk<UserCategoryRepository>()
    var viewModel = HomeFragmentViewModel(
        userCategoryRepository
    )

    Given("뷰모델이 초기 생성 되어 유저가 선택한 카테고리를 불러올 때") {

        When("로드에 성공하면") {

            coEvery {
                userCategoryRepository.getUserCategory()
            } coAnswers {
                emptyList()
            }

            viewModel = spyk(
                HomeFragmentViewModel(
                    userCategoryRepository
                )
            )

            Then("All 로 데이터를 쿼리하여 리스트에 보여준다") {
                UserCategory.typeAll()
            }
        }

        When("로드에 실패하면") {

            coEvery {
                userCategoryRepository.getUserCategory()
            } coAnswers {
                emptyList()
            }

            viewModel = spyk(
                HomeFragmentViewModel(
                    userCategoryRepository
                )
            )

            Then("3회 재시도 한다") {
            }
        }
    }

    afterTest {
        clearMocks(viewModel)
        clearMocks(userCategoryRepository)

        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
})
