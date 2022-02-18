package com.ozcoin.cookiepang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozcoin.cookiepang.adapter.viewholder.QuestionViewHolder
import com.ozcoin.cookiepang.databinding.ItemQuestionBinding
import com.ozcoin.cookiepang.domain.question.Question

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
//        holder.bind(isMine)
    }

    override fun getItemCount(): Int = 11

    fun updateList(newList: List<Question>) {
        list.clear()
        list.addAll(newList)

        notifyItemRangeChanged(0, newList.size)
    }
}
