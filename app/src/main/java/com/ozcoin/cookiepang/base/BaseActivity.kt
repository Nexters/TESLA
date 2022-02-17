package com.ozcoin.cookiepang.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.extensions.showTwoBtnDialog
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
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
            is Event.ShowDialog -> {
                handleShowDialog(event)
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

    private fun handleShowDialog(event: Event.ShowDialog) {
        showTwoBtnDialog(event.dialogContents) {
            event.callback(it)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun handleNavTo(action: NavDirections) {
        Timber.d("navigate Action(${action.javaClass.simpleName})")
        navController.navigate(action)
    }

    private fun handleNavUp() {
        Timber.d("navigate Up")
        navController.navigateUp()
    }

    protected fun handleUiState(uiState: UiState) {
        when (uiState) {
            is UiState.OnLoading -> {
                showProgress()
            }
            is UiState.OnSuccess -> {
                hideProgress()
            }
            is UiState.OnFail -> {
                hideProgress()
            }
        }
    }

    private var progressBar: View? = null

    private fun showProgress() {
        if (!isProgressShow()) {
            val rootView = (window.decorView.rootView as ViewGroup)
            progressBar = inflateProgressLayout()
            progressBar?.setOnClickListener { }

            rootView.addView(progressBar)
            progressBar?.bringToFront()
        }
    }

    private fun hideProgress() {
        if (isProgressShow()) {
            removeProgressBarView()
        }
    }

    private fun removeProgressBarView() {
        (window.decorView.rootView as ViewGroup).removeView(progressBar)
        progressBar = null
    }

    private fun isProgressShow(): Boolean = progressBar != null

    private fun inflateProgressLayout(): View {
        return (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.layout_circle_progress,
            null
        )
    }
}
