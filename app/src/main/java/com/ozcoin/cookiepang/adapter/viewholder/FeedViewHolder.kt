package com.ozcoin.cookiepang.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemFeedHiddenBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOpenedBinding
import com.ozcoin.cookiepang.domain.feed.Feed

abstract class FeedViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        val VIEW_TYPE_HIDDEN = 8810
        val VIEW_TYPE_OPENED = 8811
    }

    abstract fun bind(feed: Feed)
}

class FLAOpenedViewHolder(
    private val binding: ItemFeedOpenedBinding
) : FeedViewHolder(binding) {
    override fun bind(feed: Feed) {
    }
}

class FLAHiddenViewHolder(
    private val binding: ItemFeedHiddenBinding
) : FeedViewHolder(binding) {
    override fun bind(feed: Feed) {
    }
}
