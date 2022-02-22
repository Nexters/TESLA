package com.ozcoin.cookiepang.domain.alarm

import com.ozcoin.cookiepang.data.notification.NotificationRemoteDataSource
import com.ozcoin.cookiepang.data.notification.toDomain
import com.ozcoin.cookiepang.data.request.NetworkResult
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
            val response = notificationRemoteDataSource.getNotificationList(userId)
            if (response is NetworkResult.Success) {
                val result = convertNotificationListToAlarmsList(
                    response.response.map { it.toDomain() }
                )
                DataResult.OnSuccess(result)
            } else {
                DataResult.OnFail
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
