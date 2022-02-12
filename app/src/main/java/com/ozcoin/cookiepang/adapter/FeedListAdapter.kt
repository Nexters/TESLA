package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.FLAHiddenViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FLAOpenedViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FeedViewHolder
import com.ozcoin.cookiepang.databinding.ItemFeedHiddenBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOpenedBinding
import com.ozcoin.cookiepang.domain.feed.Feed

class FeedListAdapter : RecyclerView.Adapter<FeedViewHolder>() {

    private val list = mutableListOf<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return if (viewType == FeedViewHolder.VIEW_TYPE_OPENED) {
            val binding =
                ItemFeedOpenedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FLAOpenedViewHolder(binding)
        } else {
            val binding =
                ItemFeedHiddenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FLAHiddenViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        if (holder is FLAOpenedViewHolder) {
//            holder.bind()
        } else if (holder is FLAHiddenViewHolder) {
//            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
//        return if (list[position].isHidden) {
//
//        }
    }

    fun updateList(newList: List<Feed>) {
    }
}
