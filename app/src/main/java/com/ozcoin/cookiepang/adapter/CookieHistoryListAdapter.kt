package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ozcoin.cookiepang.adapter.viewholder.CookieHistoryViewHolder
import com.ozcoin.cookiepang.databinding.ItemCookieHistoryBinding
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory

class CookieHistoryListAdapter :
    ListAdapter<CookieHistory, CookieHistoryViewHolder>(COOKIE_HISTORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookieHistoryViewHolder {
        val binding =
            ItemCookieHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CookieHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookieHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val COOKIE_HISTORY_COMPARATOR = object : DiffUtil.ItemCallback<CookieHistory>() {
            override fun areItemsTheSame(oldItem: CookieHistory, newItem: CookieHistory): Boolean {
                return oldItem.contents == newItem.contents
            }

            override fun areContentsTheSame(
                oldItem: CookieHistory,
                newItem: CookieHistory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
