package com.ozcoin.cookiepang

import io.kotest.core.spec.style.BehaviorSpec

class SplashActivityViewModelTest : BehaviorSpec() {
    init {
        Given("유저 정보가 주어졌을때") {

            When("로그인 상태가 아니라면") {

                Then("Klip 계정 연동을 시도한다") {
                }
            }
            When("로그인 상태라면") {

                Then("메인 화면에 진입한다") {
                }
            }
        }
    }
}
