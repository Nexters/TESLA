package com.ozcoin.cookiepang.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemCookieBinding
import com.ozcoin.cookiepang.domain.cookie.Cookie

class CookieListViewHolder(
    private val binding: ItemCookieBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cookie: Cookie, onClick: (Cookie) -> Unit) {
        with(binding) {
            this.cookie = cookie
            binding.root.setOnClickListener { onClick(cookie) }
        }
    }
}
