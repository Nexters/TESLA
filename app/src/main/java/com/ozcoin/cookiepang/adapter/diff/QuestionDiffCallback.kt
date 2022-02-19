package com.ozcoin.cookiepang.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ozcoin.cookiepang.domain.question.Question

class QuestionDiffCallback(
    private val oldList: List<Question>,
    private val newList: List<Question>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].questionId == newList[newItemPosition].questionId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
