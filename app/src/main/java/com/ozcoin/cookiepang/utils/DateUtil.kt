package com.ozcoin.cookiepang.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
object DateUtil {

    private object TimeMaximum {
        const val SEC = 60
        const val MIN = 60
        const val HOUR = 24
        const val DAY = 30
        const val MONTH = 12
    }

    private val serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun convertToAppTimeStamp(date: Date): String {
        val curTime = System.currentTimeMillis()
        val regTime: Long = date.time
        var diffTime = (curTime - regTime) / 1000
        var msg: String? = null
        if (diffTime < TimeMaximum.SEC) {
            // sec
            msg = "${diffTime}초 전"
        } else if (TimeMaximum.SEC.let { diffTime /= it; diffTime } < TimeMaximum.MIN) {
            // min
            println(diffTime)
            msg = "${diffTime}분 전"
        } else if (TimeMaximum.MIN.let { diffTime /= it; diffTime } < TimeMaximum.HOUR) {
            // hour
            msg = "${diffTime}시간 전"
        } else if (TimeMaximum.HOUR.let { diffTime /= it; diffTime } < TimeMaximum.DAY) {
            // day
            msg = "${diffTime}일 전"
        } else if (TimeMaximum.DAY.let { diffTime /= it; diffTime } < TimeMaximum.MONTH) {
            // day
            msg = "${diffTime}달 전"
        } else {
            msg = "${diffTime}년 전"
        }

        return msg
    }

    fun convertToAppTimeStamp(createdAt: String): String {
        val createdAt = createdAt.split(".")[0]

        val date = kotlin.runCatching { serverFormat.parse(createdAt) }.getOrNull()

        val format = SimpleDateFormat("yyyy년 MM월 dd일").apply {
            timeZone = TimeZone.getDefault()
        }

        if (date != null) {
            val curTime = System.currentTimeMillis()
            val regTime: Long = date.time
            var diffTime = (curTime - regTime) / 1000
            val msg: String
            if (diffTime < TimeMaximum.SEC) {
                // sec
                msg = "${diffTime}초 전"
            } else if (TimeMaximum.SEC.let { diffTime /= it; diffTime } < TimeMaximum.MIN) {
                // min
                println(diffTime)
                msg = "${diffTime}분 전"
            } else if (TimeMaximum.MIN.let { diffTime /= it; diffTime } < TimeMaximum.HOUR) {
                // hour
                msg = "${diffTime}시간 전"
            } else if (TimeMaximum.HOUR.let { diffTime /= it; diffTime } < 8) {
                // 7 days
                msg = "${diffTime}일 전"
            } else {
                msg = kotlin.runCatching { format.format(date) }.getOrDefault(createdAt)
            }
            return msg
        } else {
            return createdAt
        }
    }

    fun convertToAlarmsTimeStamp(createdAt: String): String {
        val createdAt = createdAt.split(".")[0]
        val date = kotlin.runCatching { serverFormat.parse(createdAt) }.getOrNull()
        val format = SimpleDateFormat("yyyy-MM-dd")
        format.timeZone = TimeZone.getDefault()
        return if (date != null) {
            format.format(date)
        } else {
            createdAt
        }
    }

    fun convertToAlarmTimeStamp(createdAt: String): String {
        val createdAt = createdAt.split(".")[0]
        val date = kotlin.runCatching { serverFormat.parse(createdAt) }.getOrNull()
        val format = SimpleDateFormat("HH:mm")
        format.timeZone = TimeZone.getDefault()
        return if (date != null) {
            format.format(date)
        } else {
            createdAt
        }
    }
}
