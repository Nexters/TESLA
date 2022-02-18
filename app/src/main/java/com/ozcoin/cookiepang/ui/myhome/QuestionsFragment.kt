package com.ozcoin.cookiepang.ui.myhome

import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.QuestionListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentQuestionsBinding
import com.ozcoin.cookiepang.extensions.toDp
import com.ozcoin.cookiepang.ui.divider.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionsFragment : BaseFragment<FragmentQuestionsBinding>() {

    private val myHomeFragmentViewModel by viewModels<MyHomeFragmentViewModel>(ownerProducer = { requireParentFragment() })
    private lateinit var questionListAdapter: QuestionListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_questions
    }

    override fun initView() {
        setupQuestionList()
    }

    private fun setupQuestionList() {
        with(binding.rvQuestions) {
            viewLifecycleScope.launch {
                val isMine = myHomeFragmentViewModel.userInfo.first()?.isMine ?: false
//                val isMine = false
                questionListAdapter = QuestionListAdapter(isMine)

                if (isMine) {
                    questionListAdapter.apply {
                        acceptClick = {
                        }
                        ignoreClick = {
                        }
                    }
                }

                addItemDecoration(SpaceItemDecoration(16.toDp()))
                adapter = questionListAdapter
            }
        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
    }

    override fun init() {
    }
}
