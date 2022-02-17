package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryCategoryViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryResetViewHolder
import com.ozcoin.cookiepang.adapter.viewholder.UserCategoryViewHolder
import com.ozcoin.cookiepang.databinding.ItemUserCategoryBinding
import com.ozcoin.cookiepang.databinding.ItemUserCategoryResetBinding
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

class UserCategoryListAdapter(
    private val itHasResetCategoryItem: Boolean
) : RecyclerView.Adapter<UserCategoryViewHolder>() {

    private val list = mutableListOf<UserCategory>()

    private val tempItemLen = if (itHasResetCategoryItem) 1 else 0
    private var selectedPos = -1
    var onItemClick: ((UserCategory?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCategoryViewHolder {
        return if (viewType == UserCategoryViewHolder.VIEW_TYPE_RESET) {
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
            val userCategory = list[position - tempItemLen]
            holder.bind(userCategory) {
                if (selectedPos != position) {
                    changeSelectedCategory(selectedPos, position)
                    onItemClick?.invoke(userCategory)
                }
            }
        } else {
            holder.bind(null) {
                onItemClick?.invoke(null)
            }
        }
    }

    private fun changeSelectedCategory(prePos: Int, selectPos: Int) {
        if (selectedPos != -1 && prePos != selectPos) {
            list[prePos - tempItemLen].isSelected = false
            notifyItemChanged(prePos)
        }

        list[selectPos - tempItemLen].isSelected = true
        selectedPos = selectPos

        notifyItemChanged(selectPos)
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.size + tempItemLen
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && itHasResetCategoryItem) {
            UserCategoryViewHolder.VIEW_TYPE_RESET
        } else {
            UserCategoryViewHolder.VIEW_TYPE_CATEGORY
        }
    }

    fun getUserCategoryList() = list

    fun updateList(newList: List<UserCategory>) {
        list.clear()
        list.addAll(newList)

        list.forEachIndexed { index, userCategory ->
            if (userCategory.isSelected) {
                selectedPos = index + tempItemLen
                return@forEachIndexed
            }
        }

        notifyItemRangeInserted(0, newList.size + tempItemLen)
    }
}
