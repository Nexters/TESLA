package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.CALViewHolder
import com.ozcoin.cookiepang.databinding.ItemCategoryBinding

class CategoryListAdapter: RecyclerView.Adapter<CALViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CALViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CALViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CALViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

    fun updateList() {

    }

}