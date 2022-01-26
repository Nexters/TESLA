package com.ozcoin.cookiepang.ui.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    private val splashActivityViewModel by viewModel<SplashActivityViewModel>()
    private lateinit var navController: NavController

    init {
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                delay(1000)
            }

            if (splashActivityViewModel.isUserReg()) {
                navController.navigate(SplashFragmentDirections.actionMain())
                finish()
            } else {
                navController.navigate(SplashFragmentDirections.actionLogin())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(binding.fcvNavHost.id) as NavHostFragment).navController

        initObserve()
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