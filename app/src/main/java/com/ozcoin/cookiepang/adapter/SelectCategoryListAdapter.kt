package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.viewholder.SCLAViewHolder
import com.ozcoin.cookiepang.databinding.ItemSelectCategoryBinding
import com.ozcoin.cookiepang.domain.selectcategory.SelectCategory

class SelectCategoryListAdapter : RecyclerView.Adapter<SCLAViewHolder>() {

    private var list = mutableListOf<SelectCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SCLAViewHolder {
        val binding: ItemSelectCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_select_category,
            parent,
            false
        )
        return SCLAViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SCLAViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item) {
            item.isSelected = !item.isSelected
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<SelectCategory>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size - 1)
    }

    fun getSelectedCategory(): List<SelectCategory> {
        return list.filter {
            it.isSelected
        }
    }
}
