package com.ozcoin.cookiepang.domain.alarm

data class Alarm(
    val type: AlarmType,
    val alarmId: Int,
    val title: String,
    val contents: String,
    val time: String,
    val askId: Int?,
    val cookieId: String
)
