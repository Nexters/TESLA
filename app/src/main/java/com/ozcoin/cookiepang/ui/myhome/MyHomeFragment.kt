package com.ozcoin.cookiepang.ui.myhome

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.MyHomeViewPagerAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentMyHomeBinding
import com.ozcoin.cookiepang.databinding.ItemMyHomeTabBinding
import com.ozcoin.cookiepang.domain.userinfo.UserInfo
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MyHomeFragment : BaseFragment<FragmentMyHomeBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val myHomeFragmentViewModel by viewModels<MyHomeFragmentViewModel>()
    private lateinit var myHomeViewPagerAdapter: MyHomeViewPagerAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_home
    }

    override fun initView() {
        with(binding) {
            isShareAvailable = true
            viewModel = myHomeFragmentViewModel
        }
        setupViewPager()
    }

    private fun setupViewPager() {
        with(binding.vpPager) {
            myHomeViewPagerAdapter = MyHomeViewPagerAdapter(this@MyHomeFragment)
            isUserInputEnabled = false
            adapter = myHomeViewPagerAdapter
        }
    }

    override fun initListener() {
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
        observeUserInfo()
        myHomeFragmentViewModel.uiStateObserver =
            UiStateObserver(mainActivityViewModel::updateUiState)
        myHomeFragmentViewModel.eventObserver = EventObserver(mainActivityViewModel::updateEvent)
    }

    private fun observeUserInfo() {
        viewLifecycleScope.launch {
            myHomeFragmentViewModel.userInfo.first { it != null }?.let {
                setupTabLayout(it)
            }
        }
    }

    private fun setupTabLayout(userInfo: UserInfo) {
        TabLayoutMediator(binding.tlTabLayout, binding.vpPager) { tab, position ->
            val binding = ItemMyHomeTabBinding.inflate(layoutInflater, null, false)
            when (position) {
                0 -> {
                    binding.count = userInfo.collectedCnt.toString()
                    binding.tabName = "Collected"
                }
                1 -> {
                    binding.count = userInfo.createdCnt.toString()
                    binding.tabName = "Created"
                }
                2 -> {
                    binding.count = userInfo.questionCnt.toString()
                    binding.tabName = "Questions"
                }
                else -> {}
            }

            tab.customView = binding.root
        }.attach()
    }
}
