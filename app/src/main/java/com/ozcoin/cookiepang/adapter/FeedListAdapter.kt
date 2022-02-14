package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.diff.FeedDiffCallback
import com.ozcoin.cookiepang.adapter.viewholder.FeedHiddenViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FeedOpenedViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.FeedViewHolder
import com.ozcoin.cookiepang.databinding.ItemFeedHiddenBinding
import com.ozcoin.cookiepang.databinding.ItemFeedOpenedBinding
import com.ozcoin.cookiepang.domain.feed.Feed
import timber.log.Timber

class FeedListAdapter : RecyclerView.Adapter<FeedViewHolder>() {

    private val list = mutableListOf<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return if (viewType == FeedViewHolder.VIEW_TYPE_OPENED) {
            val binding =
                ItemFeedOpenedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FeedOpenedViewHolder(binding)
        } else {
            val binding =
                ItemFeedHiddenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FeedHiddenViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(list[position]) {
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].isHidden) {
            FeedViewHolder.VIEW_TYPE_HIDDEN
        } else {
            FeedViewHolder.VIEW_TYPE_OPENED
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
