package com.ozcoin.cookiepang.adapter.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.AlarmListAdapter
import com.ozcoin.cookiepang.databinding.ItemAlarmsBinding
import com.ozcoin.cookiepang.domain.alarm.Alarm
import com.ozcoin.cookiepang.domain.alarm.Alarms
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.divider.SingleLineItemDecoration

class AlarmsViewHolder(
    private val binding: ItemAlarmsBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val alarmListAdapter = AlarmListAdapter()

    fun bind(alarms: Alarms, onClick: (Alarm) -> Unit) {
        with(binding) {
            this.alarms = alarms
            rvAlarm.addItemDecoration(
                SingleLineItemDecoration(
                    1.toDp(),
                    ContextCompat.getColor(binding.root.context, R.color.gray_30_sur2_bg2)
                )
            )

            alarmListAdapter.onItemClick = { onClick(it) }
            rvAlarm.adapter = alarmListAdapter
            alarmListAdapter.updateList(alarms.alarmList)
        }
    }
}
