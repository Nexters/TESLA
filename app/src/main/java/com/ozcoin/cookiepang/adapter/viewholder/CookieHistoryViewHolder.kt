package com.ozcoin.cookiepang.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemCookieHistoryBinding
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory

class CookieHistoryViewHolder(
    private val binding: ItemCookieHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cookieHistory: CookieHistory) {
        binding.cookieHistory = cookieHistory
    }
}
