package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.AlarmViewHolder
import com.ozcoin.cookiepang.databinding.ItemAlarmBinding
import com.ozcoin.cookiepang.domain.alarm.Alarm

class AlarmListAdapter : RecyclerView.Adapter<AlarmViewHolder>() {

    private val list = mutableListOf<Alarm>()

    var onItemClick: ((Alarm) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(list[position]) {
            onItemClick?.invoke(it)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Alarm>) {
        list.clear()
        list.addAll(newList)

        notifyItemRangeInserted(0, newList.size)
    }
}
