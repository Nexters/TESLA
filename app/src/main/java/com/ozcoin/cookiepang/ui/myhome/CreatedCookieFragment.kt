package com.ozcoin.cookiepang.ui.myhome

import androidx.fragment.app.viewModels
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.CookieListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentCreatedCookieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatedCookieFragment : BaseFragment<FragmentCreatedCookieBinding>() {

    private val myHomeFragmentViewModel by viewModels<MyHomeFragmentViewModel>(ownerProducer = { requireParentFragment() })
    private val cookieListAdapter = CookieListAdapter()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_collected_cookie
    }

    override fun initView() {
        setupCreatedCookieList()
    }

    private fun setupCreatedCookieList() {
//        with(binding.rvCreatedCookie) {
//            cookieListAdapter.apply {
//                onItemClick = {
//
//                }
//            }
//
//            layoutManager = GridLayoutManager(requireContext(), 3)
//            adapter = cookieListAdapter
//        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
    }

    override fun init() {
    }
}
