package com.ozcoin.cookiepang.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemSelectCategoryBinding
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

class SelectCategoryViewHolder(
    private val binding: ItemSelectCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userCategory: UserCategory, onClick: () -> Unit) {
        binding.userCategory = userCategory
        binding.root.setOnClickListener { onClick() }
    }
}
