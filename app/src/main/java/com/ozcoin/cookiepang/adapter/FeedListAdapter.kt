package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.FLAViewHolder
import com.ozcoin.cookiepang.databinding.ItemFeedBinding

class FeedListAdapter : RecyclerView.Adapter<FLAViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FLAViewHolder {
        val binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FLAViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FLAViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 20
    }

    fun updateList() {
    }
}
