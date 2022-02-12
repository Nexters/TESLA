package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryAllViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryCategoryViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryResetViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryViewHolder
import com.ozcoin.cookiepang.databinding.ItemUserCategoryAllBinding
import com.ozcoin.cookiepang.databinding.ItemUserCategoryBinding
import com.ozcoin.cookiepang.databinding.ItemUserCategoryResetBinding
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

class UserCategoryListAdapter : RecyclerView.Adapter<UserCategoryViewHolder>() {

    private val list = mutableListOf<UserCategory>()

    var onItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCategoryViewHolder {
        return if (viewType == UserCategoryViewHolder.VIEW_TYPE_ALL) {
            val binding =
                ItemUserCategoryAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserCategoryAllViewHolder(binding)
        } else if (viewType == UserCategoryViewHolder.VIEW_TYPE_RESET) {
            val binding =
                ItemUserCategoryResetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserCategoryResetViewHolder(binding)
        } else {
            val binding =
                ItemUserCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserCategoryCategoryViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: UserCategoryViewHolder, position: Int) {
        if (holder is UserCategoryCategoryViewHolder) {
            holder.bind(list[position - 2], onItemClick)
        } else {
            holder.bind(null, onItemClick)
        }
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            UserCategoryViewHolder.VIEW_TYPE_RESET
        } else if (position == 1) {
            UserCategoryViewHolder.VIEW_TYPE_ALL
        } else {
            UserCategoryViewHolder.VIEW_TYPE_CATEGORY
        }
    }

    fun updateList(newList: List<UserCategory>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size + 2)
    }
}
