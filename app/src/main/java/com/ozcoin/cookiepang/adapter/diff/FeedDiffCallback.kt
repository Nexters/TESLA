package com.ozcoin.cookiepang.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ozcoin.cookiepang.domain.feed.Feed

class FeedDiffCallback(
    private val oldList: List<Feed>,
    private val newList: List<Feed>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].cookieId == newList[newItemPosition].cookieId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
