package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.utils.DateUtil
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import java.text.SimpleDateFormat
import java.util.Date

class DataUtilFeature : FeatureSpec() {
    init {
        feature("createAt 서버 시간이 주어지면 피드 타임라인 시간 포맷에 맞게 표시") {
            val string = "2022-02-18T17:16:40.590408Z"
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

            var date: Date
            var time: String

            scenario("23시간 59분 59초 전에 생성되었으면 23시간 전") {

                println(string.split(".")[0])
                println(format.parse(string.split(".")[0]))

                date = Date()

                date.hours = date.hours - 23
                date.minutes = date.minutes - 59
                date.seconds = date.seconds - 59

                time = format.format(date)

                DateUtil.convertToFeedTimeStamp(time) shouldBe "23시간 전"
            }

            scenario("24시간 전에 생성되었으면 1일 전") {
                date = Date()

                date.hours = date.hours - 24

                time = format.format(date)

                DateUtil.convertToFeedTimeStamp(time) shouldBe "1일 전"
            }
        }
    }
}
