package com.ozcoin.cookiepang.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivitySplashBinding
import com.ozcoin.cookiepang.repo.user.UserRegRepository
import com.ozcoin.cookiepang.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    private val userRegRepository by inject<UserRegRepository>()
    private lateinit var navController : NavController

    init {
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                delay(1000)
            }

            if (userRegRepository.isUserReg()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else {
                navController.navigate(SplashFragmentDirections.actionLogin())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = (supportFragmentManager.findFragmentById(binding.fcvNavHost.id) as NavHostFragment).navController
    }

}