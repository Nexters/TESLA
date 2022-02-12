package com.ozcoin.cookiepang.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity @Inject constructor() : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    private val splashActivityViewModel by viewModels<SplashActivityViewModel>()

    init {
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                delay(1000)
            }

            splashActivityViewModel.isUserLogin().collect {
                Timber.d("isUserLogin : $it")
                if (it) {
                    splashActivityViewModel.navigateToMain()
                } else {
                    splashActivityViewModel.navigateToLogin()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(binding.fcvNavHost.id) as NavHostFragment).navController
    }

    override fun initObserve() {
        lifecycleScope.launch {
            splashActivityViewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun init() {
    }
}
