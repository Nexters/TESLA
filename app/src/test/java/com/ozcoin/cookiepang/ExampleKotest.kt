package com.ozcoin.cookiepang

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlin.math.roundToInt

class ExampleKotest : BehaviorSpec() {
    init {
        Given("100점이 만점인 상황에서") {
            val totalMarks = 100
            When("학생의 점수가") {
                And("90점 이상이라면") {
                    val obtainedMarks = 92
                    Then("등급은 A") {
                        getGrade(obtainedMarks, totalMarks) shouldBe "A"
                    }
                }
                And("80점 이상 90점 미만이라면") {
                    val obtainedMarks = 88
                    Then("등급은 B") { getGrade(obtainedMarks, totalMarks) shouldBe "B" }
                }
                And("70점 이상 80점 미만이라면") {
                    val obtainedMarks = 77
                    Then("등급은 C") { getGrade(obtainedMarks, totalMarks) shouldBe "C" }
                }
                And("60점 이상 70점 미만이라면") {
                    val obtainedMarks = 66
                    Then("등급은 D") { getGrade(obtainedMarks, totalMarks) shouldBe "D" }
                }
                And("60점 미만이라면") {
                    val obtainedMarks = 34
                    Then("등급은 F") { getGrade(obtainedMarks, totalMarks) shouldBe "F" }
                }
            }
        }
    }

    private fun getGrade(obtainedMarks: Int, totalMarks: Int): String {
        val percentage = getPercentage(obtainedMarks, totalMarks)
        return when {
            percentage >= 90 -> "A"
            percentage in 80..89 -> "B"
            percentage in 70..79 -> "C"
            percentage in 60..69 -> "D"
            else -> "F"
        }
    }

    private fun getPercentage(obtainedMarks: Int, totalMarks: Int): Int {
        return (obtainedMarks / totalMarks.toFloat() * 100).roundToInt()
    }
}
