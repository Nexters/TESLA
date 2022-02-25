package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.diff.FeedDiffCallback
import com.ozcoin.cookiepang.adapter.viewholder.FeedHiddenViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FeedOnLoadingViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FeedOpenedViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FeedViewHolder
import com.ozcoin.cookiepang.databinding.ItemFeedHiddenBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOnLoadingBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOpenedBinding
import com.ozcoin.cookiepang.domain.feed.Feed
import timber.log.Timber

class FeedListAdapter : RecyclerView.Adapter<FeedViewHolder>() {

    private val list = mutableListOf<Feed>()

    var onItemClick: ((Feed) -> Unit)? = null
    var onUserProfileClick: ((Feed) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return when (viewType) {
            FeedViewHolder.VIEW_TYPE_OPENED -> {
                val binding =
                    ItemFeedOpenedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FeedOpenedViewHolder(binding)
            }
            FeedViewHolder.VIEW_TYPE_HIDDEN -> {
                val binding =
                    ItemFeedHiddenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FeedHiddenViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemFeedOnLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FeedOnLoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(
            list[position],
            onClick = {
                onItemClick?.invoke(it)
            }, onUserProfileClick = {
            onUserProfileClick?.invoke(it)
        }
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return if (Feed.isLastPage(item)) {
            if (item.isHidden) {
                FeedViewHolder.VIEW_TYPE_HIDDEN
            } else {
                FeedViewHolder.VIEW_TYPE_OPENED
            }
        } else {
            FeedViewHolder.VIEW_TYPE_LOADING
        }
    }

    fun getFeedList() = list

    fun updateList(newList: List<Feed>) {
        Timber.d("updateList(size: ${newList.size})")

        val feedDiffCallback = FeedDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(feedDiffCallback)

        list.clear()
        list.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }
}
