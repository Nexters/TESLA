package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.AlarmsViewHolder
import com.ozcoin.cookiepang.databinding.ItemAlarmsBinding
import com.ozcoin.cookiepang.domain.alarm.Alarm
import com.ozcoin.cookiepang.domain.alarm.Alarms

class AlarmsListAdapter : RecyclerView.Adapter<AlarmsViewHolder>() {

    private val list = mutableListOf<Alarms>()

    var onItemClick: ((Alarm) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmsViewHolder {
        val binding = ItemAlarmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmsViewHolder, position: Int) {
        holder.bind(list[position]) {
            onItemClick?.invoke(it)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Alarms>) {
        list.clear()
        list.addAll(newList)

        notifyItemRangeInserted(0, newList.size - 1)
    }
}
