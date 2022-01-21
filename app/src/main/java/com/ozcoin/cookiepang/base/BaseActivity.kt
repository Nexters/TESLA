package com.ozcoin.cookiepang.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ozcoin.cookiepang.utils.Event
import timber.log.Timber

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {

    protected val binding: T by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes())
    }

    @LayoutRes
    protected abstract fun getLayoutRes() : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
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
        when(event) {
            is Event.StartComponent -> {
                when(event) {
                    is Event.StartComponent.Activity -> {
                        handleStartActivity(event)
                    }
                }
            }
            is Event.FinishComponent -> {
                when(event) {
                    is Event.FinishComponent.Activity -> {
                        handleFinishActivity(event)
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

}