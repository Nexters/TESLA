package com.ozcoin.cookiepang.domain.alarm

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.delay
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor() : AlarmsRepository {

    override suspend fun getAlarmsList(userId: String): DataResult<List<Alarms>> {
        delay(500L)
        return DummyUtil.getAlarmsList()
    }
}
