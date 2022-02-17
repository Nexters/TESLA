package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class SplashActivityViewModelTest : BehaviorSpec({

    var testDispatcher = TestCoroutineDispatcher()

    val klipAuthRepository = mockk<KlipAuthRepository>()
    val userRepository = mockk<UserRepository>()
    val splashActivityViewModel = spyk(
        LoginViewModel(
            userRepository, klipAuthRepository
        )
    )

    beforeTest {
        testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    Given("유저 정보가 주어졌을때") {

        When("Klip 로그인 상태가 아니라면") {

            every { klipAuthRepository.isUserLogin() } returns flow {
                emit(false)
            }

            splashActivityViewModel.isUserLogin().first().let {
                if (it) {
                    splashActivityViewModel.navigateToMain()
                } else {
                    splashActivityViewModel.navigateToLogin()
                }
            }

            Then("로그인 화면으로 이동한다") {
                verify { splashActivityViewModel.navigateToLogin() }
            }
        }
        When("Klip 로그인 상태라면") {

            every { klipAuthRepository.isUserLogin() } returns flow {
                emit(true)
            }

            splashActivityViewModel.isUserLogin().first().let {
                if (it) {
                    splashActivityViewModel.navigateToMain()
                } else {
                    splashActivityViewModel.navigateToLogin()
                }
            }

            Then("메인 화면에 진입한다") {
                verify { splashActivityViewModel.navigateToMain() }
            }
        }
    }

    afterTest {
        MockUtil.clearMocks(listOf(klipAuthRepository, splashActivityViewModel, userRepository))

        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
})
