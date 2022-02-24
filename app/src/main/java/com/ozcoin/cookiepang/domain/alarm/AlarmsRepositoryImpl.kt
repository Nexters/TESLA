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
        val alarmsList = mutableListOf<Alarms>()

        if (noticeList.isNotEmpty()) {
            val dateMap = HashMap<String, Boolean>()
            for (n in noticeList) {
                val alarmList = mutableListOf<Alarm>()

                val date = DateUtil.convertToAlarmsTimeStamp(n.time)
                if (dateMap[date] != null)
                    continue

                dateMap[date] = true

                for (a in noticeList) {
                    if (DateUtil.convertToAlarmsTimeStamp(a.time) == date) {
                        val alarm = Alarm(a.alarmId, a.title, a.contents, DateUtil.convertToAlarmTimeStamp(a.time))
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
