package com.ozcoin.cookiepang.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemFeedHiddenBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOnLoadingBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOpenedBinding
import com.ozcoin.cookiepang.domain.feed.Feed

abstract class FeedViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val VIEW_TYPE_HIDDEN = 8810
        const val VIEW_TYPE_OPENED = 8811
        const val VIEW_TYPE_LOADING = 8812
    }

    abstract fun bind(feed: Feed, onClick: (Feed) -> Unit, onUserProfileClick: (Feed) -> Unit)
}

class FeedOpenedViewHolder(
    private val binding: ItemFeedOpenedBinding
) : FeedViewHolder(binding) {
    override fun bind(feed: Feed, onClick: (Feed) -> Unit, onUserProfileClick: (Feed) -> Unit) {
        with(binding) {
            this.feed = feed
            clTopContainer.setOnClickListener { onClick(feed) }
            ivUserThumbnail.setOnClickListener { onUserProfileClick(feed) }
        }
    }
}

class FeedHiddenViewHolder(
    private val binding: ItemFeedHiddenBinding
) : FeedViewHolder(binding) {
    override fun bind(feed: Feed, onClick: (Feed) -> Unit, onUserProfileClick: (Feed) -> Unit) {
        with(binding) {
            this.feed = feed
            clTopContainer.setOnClickListener { onClick(feed) }
            ivUserThumbnail.setOnClickListener { onUserProfileClick(feed) }
        }
    }
}

class FeedOnLoadingViewHolder(
    private val binding: ItemFeedOnLoadingBinding
) : FeedViewHolder(binding) {
    override fun bind(feed: Feed, onClick: (Feed) -> Unit, onUserProfileClick: (Feed) -> Unit) {
    }
}
