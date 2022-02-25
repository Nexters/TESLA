package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.domain.contract.ContractRepository
import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetailRepository
import com.ozcoin.cookiepang.domain.klip.KlipContractTxRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.ui.cookiedetail.CookieDetailViewModel
import com.ozcoin.cookiepang.utils.DummyUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class CookieDetailViewModelBehavior : BehaviorSpec({

    val cookieRepository = mockk<CookieRepository>()
    val cookieDetailRepository = mockk<CookieDetailRepository>()
    val userRepository = mockk<UserRepository>()
    val klipContractTxRepository = mockk<KlipContractTxRepository>()
    val contractRepository = mockk<ContractRepository>()
    val cookieDetailViewModel = spyk(
        CookieDetailViewModel(
            userRepository, cookieRepository, contractRepository, cookieDetailRepository, klipContractTxRepository
        )
    )

    val coroutineTestRule: CoroutineTestRule by CoroutineTestRuleImpl()
    var testDispatcher: TestCoroutineDispatcher? = null

    var event: Event? = null
    var uiState: UiState? = null
    cookieDetailViewModel.uiStateObserver = UiStateObserver { uiState = it }
    cookieDetailViewModel.activityEventObserver = EventObserver { event = it }

    var userId = ""
    var cookieId = ""

    beforeTest {
        testDispatcher = coroutineTestRule.beforeTest()
    }

    Given("쿠키 아이디를 전달 받음") {

        When("쿠키 아이디가 비정상 문자열") {

            cookieId.shouldBeEmpty()

            Then("이전 화면으로 이동한다") {
                cookieDetailViewModel.loadCookieDetail(cookieId)
                cookieDetailViewModel.eventFlow.first().shouldBeInstanceOf<Event.Nav.Up>()
            }
        }
        When("쿠키 아이디가 정상 문자열") {

            cookieId = "cookieId"
            cookieId.shouldNotBeEmpty()

            coEvery {
                cookieDetailRepository.getCookieDetail(userId, cookieId)
            } coAnswers {
                DummyUtil.getCookieDetail(isMine = false, isHidden = true)
            }

            Then("쿠키 상세 정보 쿼리 요청") {
                cookieDetailViewModel.loadCookieDetail(cookieId)
                uiState shouldBe UiState.OnSuccess
                cookieDetailViewModel.cookieDetail.first().shouldBeInstanceOf<CookieDetail>()
            }
        }
    }

    Given("쿠키 상세 정보 로드되어 사용자가 구매/수정 버튼 클릭") {

        coEvery {
            cookieDetailRepository.getCookieDetail(userId, cookieId)
        } coAnswers {
            DummyUtil.getCookieDetail(isMine = false, isHidden = true)
        }

        cookieDetailViewModel.loadCookieDetail(cookieId)
        cookieDetailViewModel.cookieDetail.value shouldNotBe null
        uiState shouldBe UiState.OnSuccess

        When("내 쿠키 상세 정보라면") {

            every { cookieDetailViewModel.cookieDetail.value?.isMine } returns true
            every { cookieDetailViewModel.cookieDetail.value?.hammerPrice } returns 10
            every { cookieDetailViewModel.cookieDetail.value?.userCategory } returns UserCategory.typeAll()
            cookieDetailViewModel.cookieDetail.value?.isMine shouldBe true

            Then("판매 정보 수정으로 이동") {
                cookieDetailViewModel.clickCookieContentsBtn()
                event.shouldBeInstanceOf<Event.Nav.ToEditCookie>()
            }
        }

        When("내 쿠키 상세 정보가 아니라면") {

            every { cookieDetailViewModel.cookieDetail.value?.isMine } returns false
            every { cookieDetailViewModel.cookieDetail.value?.hammerPrice } returns 10
            cookieDetailViewModel.cookieDetail.value?.isMine shouldBe false

            Then("구매 확인 다이얼로그 표시") {
                cookieDetailViewModel.clickCookieContentsBtn()
                event.shouldBeInstanceOf<Event.ShowDialog>()
            }
        }
    }

    afterTest {
        MockUtil.clearMocks(listOf(cookieDetailRepository))
        coroutineTestRule.afterTest(testDispatcher!!)
    }
})
