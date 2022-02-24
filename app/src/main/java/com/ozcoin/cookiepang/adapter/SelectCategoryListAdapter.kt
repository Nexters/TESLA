package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.viewholder.SelectCategoryViewHolder
import com.ozcoin.cookiepang.databinding.ItemSelectCategoryBinding
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import kotlin.streams.toList

class SelectCategoryListAdapter : RecyclerView.Adapter<SelectCategoryViewHolder>() {

    private var list = mutableListOf<UserCategory>()

    var onItemClick: ((List<UserCategory>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCategoryViewHolder {
        val binding: ItemSelectCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_select_category,
            parent,
            false
        )
        return SelectCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectCategoryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item) {
            item.isSelected = !item.isSelected
            notifyItemChanged(position)
            onItemClick?.invoke(getSelectedCategory())
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<UserCategory>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size)
    }

    private fun getSelectedCategory(): List<UserCategory> {
        return list.stream().filter {
            it.isSelected
        }.toList()
    }
}
