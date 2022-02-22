package com.ozcoin.cookiepang.domain.alarm

data class Alarm(
    val alarmId: Int,
    val title: String,
    val contents: String,
    val time: String
)
