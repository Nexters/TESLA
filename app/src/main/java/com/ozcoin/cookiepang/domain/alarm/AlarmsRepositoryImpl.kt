package com.ozcoin.cookiepang.domain.alarm

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor() : AlarmsRepository {

    override suspend fun getAlarmsList(userId: String): DataResult<List<Alarms>> =
        withContext(Dispatchers.IO) {
            delay(500L)
            DummyUtil.getAlarmsList()
        }
}
