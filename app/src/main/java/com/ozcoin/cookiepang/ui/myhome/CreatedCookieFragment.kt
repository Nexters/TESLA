package com.ozcoin.cookiepang.ui.myhome

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.CookieListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCreatedCookieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatedCookieFragment : BaseFragment<FragmentCreatedCookieBinding>() {

    private val myHomeFragmentViewModel by viewModels<MyHomeFragmentViewModel>(ownerProducer = { requireParentFragment() })
    private val cookieListAdapter = CookieListAdapter()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_created_cookie
    }

    override fun initView() {
        setupCreatedCookieList()
    }

    private fun setupCreatedCookieList() {
        with(binding.rvCreatedCookie) {
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
        observeCreatedCookieList()
    }

    private fun observeCreatedCookieList() {
        viewLifecycleScope.launch {
            myHomeFragmentViewModel.createdCookieList.collect {
                cookieListAdapter.updateList(it)
            }
        }
    }

    override fun init() {
    }
}
