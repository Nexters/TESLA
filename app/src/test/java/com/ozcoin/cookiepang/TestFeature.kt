package com.ozcoin.cookiepang

import io.kotest.core.spec.style.FeatureSpec

class TestFeature : FeatureSpec() {
    init {
        feature("t") {

            scenario("t") {
                var string = "ㅇㅇ ㅁ"
                val regex = "[0-9|a-zA-Zㄱ-ㅎㅏ-ㅣ가-힝^_-]*".toRegex()
                string = string.replace(" ", "")
                println(string)
                println(string.matches(regex))

                string = "fire_ax -"
                println(string)
                println(string.matches(regex))
            }
        }
    }
}
