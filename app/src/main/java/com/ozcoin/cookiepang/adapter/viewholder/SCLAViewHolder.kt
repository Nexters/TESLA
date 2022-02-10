package com.ozcoin.cookiepang.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemSelectCategoryBinding
import com.ozcoin.cookiepang.domain.selectcategory.SelectCategory

class SCLAViewHolder(
    private val binding: ItemSelectCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(selectCategory: SelectCategory, onClick: () -> Unit) {
        binding.selectCategory = selectCategory
        binding.root.setOnClickListener { onClick() }
    }
}
