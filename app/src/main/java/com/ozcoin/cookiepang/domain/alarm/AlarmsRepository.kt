package com.ozcoin.cookiepang.domain.alarm

import com.ozcoin.cookiepang.utils.DataResult

interface AlarmsRepository {

    suspend fun getAlarmsList(userId: String): DataResult<List<Alarms>>
}
