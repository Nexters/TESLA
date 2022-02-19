package com.ozcoin.cookiepang.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemAlarmBinding
import com.ozcoin.cookiepang.domain.alarm.Alarm

class AlarmViewHolder(
    private val binding: ItemAlarmBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(alarm: Alarm, onClick: (Alarm) -> Unit) {
        with(binding) {
            this.alarm = alarm
            root.setOnClickListener { onClick(alarm) }
        }
    }
}
