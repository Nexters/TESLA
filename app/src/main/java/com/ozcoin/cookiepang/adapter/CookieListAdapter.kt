package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.CookieListViewHolder
import com.ozcoin.cookiepang.databinding.ItemCookieBinding
import com.ozcoin.cookiepang.domain.cookie.Cookie

class CookieListAdapter : RecyclerView.Adapter<CookieListViewHolder>() {

    private val list = mutableListOf<Cookie>()

    var onItemClick: ((Cookie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookieListViewHolder {
        val binding = ItemCookieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CookieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookieListViewHolder, position: Int) {
//        holder.bind(list[position]) {
//            onItemClick?.invoke(it)
//        }
    }

    override fun getItemCount(): Int = 10

    fun updateList(newList: List<Cookie>) {
        list.clear()
        list.addAll(newList)

        notifyItemRangeChanged(0, newList.size)
    }
}
