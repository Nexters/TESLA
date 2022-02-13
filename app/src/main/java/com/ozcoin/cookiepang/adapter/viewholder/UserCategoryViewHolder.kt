package com.ozcoin.cookiepang.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemUserCategoryBinding
import com.ozcoin.cookiepang.databinding.ItemUserCategoryResetBinding
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

abstract class UserCategoryViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val VIEW_TYPE_RESET = 5501
        const val VIEW_TYPE_CATEGORY = 5502
    }

    abstract fun bind(userCategory: UserCategory?, onClick: (() -> Unit)?)
}

class UserCategoryResetViewHolder(
    private val binding: ItemUserCategoryResetBinding
) : UserCategoryViewHolder(binding) {

    override fun bind(userCategory: UserCategory?, onClick: (() -> Unit)?) {
        with(binding) {
            root.setOnClickListener { onClick?.invoke() }
        }
    }
}

class UserCategoryCategoryViewHolder(
    private val binding: ItemUserCategoryBinding
) : UserCategoryViewHolder(binding) {

    override fun bind(userCategory: UserCategory?, onClick: (() -> Unit)?) {
        with(binding) {
            this.userCategory = userCategory
            root.setOnClickListener { onClick?.invoke() }
        }
    }
}
