package com.ozcoin.cookiepang.ui.myhome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.MyHomeViewPagerAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentMyHomeBinding
import com.ozcoin.cookiepang.databinding.ItemMyHomeTabBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyHomeFragment : BaseFragment<FragmentMyHomeBinding>() {

    private val myHomeFragmentViewModel by viewModels<MyHomeFragmentViewModel>()
    private lateinit var myHomeViewPagerAdapter: MyHomeViewPagerAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_home
    }

    override fun initView() {
        with(binding) {
            isShareAvailable = true
        }
        setupViewPager()
        setupTabLayout()
        bindTabWithViewPager()
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tlTabLayout, binding.vpPager) { tab, position ->
            val binding = ItemMyHomeTabBinding.inflate(layoutInflater, null, false)
            binding.count = "100"
            binding.tabName = when (position) {
                0 -> "Collected"
                1 -> "Created"
                2 -> "Questions"
                else -> ""
            }
            tab.customView = binding.root
        }.attach()
    }

    private fun setupViewPager() {
        with(binding.vpPager) {
            myHomeViewPagerAdapter = MyHomeViewPagerAdapter(this@MyHomeFragment)
            adapter = myHomeViewPagerAdapter
        }
    }

    private fun bindTabWithViewPager() {
    }

    override fun initListener() {
        binding.includeTitleLayout.ivBackBtn.setOnClickListener {
            myHomeFragmentViewModel.clickBack()
        }
    }

    override fun init() {
        myHomeFragmentViewModel.loadUserInfo(getUserId())
    }

    private fun getUserId(): String? {
        val args by navArgs<MyHomeFragmentArgs>()
        return args.userId.also {
            Timber.d("request UserId : $it")
        }
    }

    override fun initObserve() {
        observeEvent(myHomeFragmentViewModel)
    }
}
