package com.ozcoin.cookiepang.ui.myhome

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.CookieListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCollectedCookieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectedCookieFragment : BaseFragment<FragmentCollectedCookieBinding>() {

    private val myHomeFragmentViewModel by viewModels<MyHomeFragmentViewModel>(ownerProducer = { requireParentFragment() })
    private val cookieListAdapter = CookieListAdapter()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_collected_cookie
    }

    override fun initView() {
        setupCollectedCookieList()
    }

    private fun setupCollectedCookieList() {
        with(binding.rvCollectedCookie) {
            cookieListAdapter.apply {
                onItemClick = {
                    myHomeFragmentViewModel.navigateToCookieDetail(it.cookieId)
                }
            }

            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = cookieListAdapter
        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
        observeCollectedCookieList()
    }

    private fun observeCollectedCookieList() {
        viewLifecycleScope.launch {
            myHomeFragmentViewModel.collectedCookieList.collect {
                cookieListAdapter.updateList(it)
            }
        }
    }

    override fun init() {
    }
}
