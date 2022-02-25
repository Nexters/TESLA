package com.ozcoin.cookiepang

import com.ozcoin.cookiepang.utils.DateUtil
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class DateUtilFeature : FeatureSpec() {
    init {
        feature("createAt 서버 시간이 주어지면 피드 타임라인 시간 포맷에 맞게 표시") {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss").apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            var date: Date
            var time: String

            scenario("23시간 59분 59초 전에 생성되었으면 23시간 전") {

                date = Date()

                date.hours = date.hours - 23
                date.minutes = date.minutes - 59
                date.seconds = date.seconds - 59

                time = format.format(date)
                println(DateUtil.convertToAppTimeStamp(time))
                DateUtil.convertToAppTimeStamp(time) shouldBe "23시간 전"
            }

            scenario("24시간 전에 생성되었으면 1일 전") {
                date = Date()
                date.hours = date.hours - 24

                time = format.format(date)
                println(DateUtil.convertToAppTimeStamp(time))

                DateUtil.convertToAppTimeStamp(time) shouldBe "1일 전"
            }

            scenario("7일 전이면 X일 전") {
                date = Date()
                date.date = date.date - 7

                time = format.format(date)

                println(DateUtil.convertToAppTimeStamp(time))
                DateUtil.convertToAppTimeStamp(time) shouldBe "7일 전"
            }

            scenario("7일 이상 이전이면 yyyy년 MM월 dd일") {
                date = Date()
                date.date = date.date - 14

                time = format.format(date)
                println(DateUtil.convertToAppTimeStamp(time))
            }
        }
    }
}
