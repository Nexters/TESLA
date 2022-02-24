package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.diff.QuestionDiffCallback
import com.ozcoin.cookiepang.adapter.viewholder.QuestionViewHolder
import com.ozcoin.cookiepang.databinding.ItemQuestionBinding
import com.ozcoin.cookiepang.domain.question.Question
import timber.log.Timber

class QuestionListAdapter(
    private val isMine: Boolean
) : RecyclerView.Adapter<QuestionViewHolder>() {

    private val list = mutableListOf<Question>()

    var acceptClick: ((Question) -> Unit)? = null
    var ignoreClick: ((Question) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding =
            ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(isMine, list[position], acceptClick, ignoreClick)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Question>) {
        Timber.d("update QuestionList with(${newList.size})")
        val callback = QuestionDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(callback)

        list.clear()
        list.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }
}
