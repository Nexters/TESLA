package com.ozcoin.cookiepang.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.databinding.ItemQuestionBinding
import com.ozcoin.cookiepang.domain.question.Question

class QuestionViewHolder(
    private val binding: ItemQuestionBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        isMine: Boolean,
        question: Question,
        acceptClick: ((Question) -> Unit)? = null,
        ignoreClick: ((Question) -> Unit)? = null
    ) {
        with(binding) {
            this.isMine = isMine
            this.question = question

            tvAcceptBtn.setOnClickListener {
                acceptClick?.invoke(question)
            }
            tvIgnoreBtn.setOnClickListener {
                ignoreClick?.invoke(question)
            }
        }
    }
}
