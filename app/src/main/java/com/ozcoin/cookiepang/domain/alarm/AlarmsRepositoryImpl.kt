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
                val result = convertNotificationListToAlarmsList(
                    res.map { it.toDomain() }
                )
                result
            }
        }

    private fun convertNotificationListToAlarmsList(noticeList: List<Alarm>): List<Alarms> {
        val alarmsList = mutableListOf<Alarms>()

        if (noticeList.isNotEmpty()) {
            for (n in noticeList) {
                val alarmList = mutableListOf<Alarm>()

                val date = DateUtil.convertToAlarmsTimeStamp(n.time)
                for (a in noticeList) {
                    if (DateUtil.convertToAlarmTimeStamp(a.time) == date) {
                        val alarm = Alarm(a.alarmId, a.title, a.contents, a.time)
                        alarmList.add(alarm)
                    }
                }
                val alarms = Alarms(date, alarmList)
                alarmsList.add(alarms)
            }
        }
        return alarmsList
    }
}
