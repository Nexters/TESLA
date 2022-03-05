package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.data.response.BalanceResponse
import com.ozcoin.cookiepang.utils.CoinUnitUtil
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class CoinUnitUtilFeature : FeatureSpec() {
    init {
        feature("보우 망치 갯수가 주어지면") {
//            val response = "{\"balance\":1040000000000000000000}"
            val response = "{\"balance\":1000000000000000000000000000000}"
            val string = Json.decodeFromString<BalanceResponse>(response)

            println(string.balance)

            scenario("클레튼 단위로 변환(10^18)") {
                val balance = CoinUnitUtil.convertToKlaytnUnit(string.balance)
                println(balance)

                balance.toString().shouldBe("1000000000000")
            }
        }
    }
}
