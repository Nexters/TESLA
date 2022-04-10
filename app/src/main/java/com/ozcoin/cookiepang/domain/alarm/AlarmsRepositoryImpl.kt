package com.ozcoin.cookiepang.domain.alarm

import com.ozcoin.cookiepang.data.notification.NotificationRemoteDataSource
import com.ozcoin.cookiepang.data.notification.toDomain
import com.ozcoin.cookiepang.extensions.getDataResult
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource
) : AlarmsRepository {

    override suspend fun getAlarmsList(userId: String): DataResult<List<Alarms>> =
        withContext(Dispatchers.IO) {
            getDataResult(notificationRemoteDataSource.getNotificationList(userId)) { res ->
                convertNotificationListToAlarmsList(
                    res.map { it.toDomain() }
                )
            }
        }

    private fun convertNotificationListToAlarmsList(noticeList: List<Alarm>): List<Alarms> {
        return noticeList
            .groupBy { DateUtil.convertToAlarmsTimeStamp(it.time) }
            .map {
                val alarms = it.value.map { alarm ->
                    alarm.copy(time = DateUtil.convertToAlarmTimeStamp(alarm.time))
                }.sortedByDescending { alarm -> alarm.time }
                Alarms(it.key, alarms)
            }
            .sortedByDescending { it.date }
    }
}
