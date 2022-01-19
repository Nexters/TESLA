package com.ozcoin.cookiepang.ui.splash

import android.os.Bundle
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivitySplashBinding

class SplashActivity: BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

//    init {
//        lifecycleScope.launchWhenStarted {
//            withContext(Dispatchers.Default) {
//                delay(1000L)
//            }
//
//            handleEvent(Event.FinishComponent.Activity)
//            handleEvent(Event.StartComponent.Activity(LoginActivity::class.java))
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

}