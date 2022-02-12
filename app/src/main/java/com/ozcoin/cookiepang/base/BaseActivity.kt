package com.ozcoin.cookiepang.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.ozcoin.cookiepang.utils.Event
import timber.log.Timber

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected val binding: T by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes())
    }

    protected lateinit var navController: NavController

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun initView()

    protected abstract fun initListener()

    protected abstract fun initObserve()

    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        initView()
        initObserve()
        initListener()
        init()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("called")
        val nightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Timber.d("bright mode")
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                Timber.d("dark mode")
            }
        }
    }

    protected fun handleEvent(event: Event) {
        when (event) {
            is Event.StartComponent -> {
                when (event) {
                    is Event.StartComponent.Activity -> {
                        handleStartActivity(event)
                    }
                }
            }
            is Event.FinishComponent -> {
                when (event) {
                    is Event.FinishComponent.Activity -> {
                        handleFinishActivity(event)
                    }
                }
            }
            is Event.Nav -> {
                when (event) {
                    is Event.Nav.To -> {
                        handleNavTo(event.action)
                    }
                    is Event.Nav.Up -> {
                        handleNavUp()
                    }
                }
            }
        }
    }

    private fun handleStartActivity(event: Event.StartComponent.Activity) {
        startActivity(Intent(this, event.target))
    }

    private fun handleFinishActivity(event: Event.FinishComponent.Activity) {
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun handleNavTo(action: NavDirections) {
        Timber.d("navigate to : ${action.javaClass.simpleName}")
        navController.navigate(action)
    }

    private fun handleNavUp() {
        Timber.d("navigate Up")
        navController.navigateUp()
    }
}
