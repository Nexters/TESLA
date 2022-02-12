package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.ui.splash.SplashActivityViewModel
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class SplashActivityViewModelTest : BehaviorSpec({

    val testDispatcher = TestCoroutineDispatcher()
    Dispatchers.setMain(testDispatcher)

    Given("유저 정보가 주어졌을때") {

        val klipAuthRepository = mockk<KlipAuthRepository>()
        val userRepository = mockk<UserRepository>()
        var splashActivityViewModel = SplashActivityViewModel(
            userRepository, klipAuthRepository
        )

        When("Klip 로그인 상태가 아니라면") {

            splashActivityViewModel = spyk(
                SplashActivityViewModel(
                    userRepository, klipAuthRepository
                )
            )

            every { klipAuthRepository.isUserLogin() } returns flow {
                emit(false)
            }

            splashActivityViewModel.isUserLogin().collect {
                if (it) {
                    splashActivityViewModel.navigateToMain()
                } else {
                    splashActivityViewModel.navigateToLogin()
                }
            }

            every { klipAuthRepository.isUserLogin() } returns flow {
                emit(false)
            }

            Then("로그인 화면으로 이동한다") {
                verify { splashActivityViewModel.navigateToLogin() }
            }
        }
        When("Klip 로그인 상태라면") {

            splashActivityViewModel = spyk(
                SplashActivityViewModel(
                    userRepository, klipAuthRepository
                )
            )

            every { klipAuthRepository.isUserLogin() } returns flow {
                emit(true)
            }

            splashActivityViewModel.isUserLogin().collect {
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

        afterTest {
            clearMocks(userRepository, klipAuthRepository, splashActivityViewModel)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }
})
