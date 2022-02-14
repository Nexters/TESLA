package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.CookieHistoryViewHolder
import com.ozcoin.cookiepang.databinding.ItemCookieHistoryBinding
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory

class CookieHistoryListAdapter : RecyclerView.Adapter<CookieHistoryViewHolder>() {

    private val list = mutableListOf<CookieHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookieHistoryViewHolder {
        val binding =
            ItemCookieHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CookieHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookieHistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<CookieHistory>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeChanged(0, list.size)
    }
}
