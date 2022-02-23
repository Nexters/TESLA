package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.utils.CoinUnitUtil
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class CoinUnitUtilFeature : FeatureSpec() {
    init {
        feature("보우 망치 갯수가 주어지면") {
            // 1080000000000000000000
            val amount = 108000000000000000.toDouble()

            scenario("클레튼 단위로 변환") {
                println(getUnit())
                println(CoinUnitUtil.convertToKlaytnUnit(amount))
                println(amount / getUnit())
                true.shouldBeInstanceOf<Boolean>()
            }
        }
    }

    private fun getUnit(): Double {
        var unit = 10.toDouble()
        repeat(18) {
            unit *= unit
        }
        return unit
    }
}
