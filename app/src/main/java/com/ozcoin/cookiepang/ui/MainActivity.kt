package com.ozcoin.cookiepang.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivityMainBinding
import com.ozcoin.cookiepang.extensions.showTwoBtnDialog
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
open class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        with(binding) {
            viewModel = mainActivityViewModel
            navController =
                (supportFragmentManager.findFragmentById(binding.fcvNavHost.id) as NavHostFragment).navController

            setUpBtmNav()
        }
    }

    private fun setUpBtmNav() {
        with(binding.includeBtmNavLayout.customBtmNav) {
            setupWithNavController(navController)
            itemIconTintList = null
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home_dest -> {
                        navigateToHome()
                    }
                    R.id.my_home_dest -> {
                        navigateToMyHome()
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun navigateToHome() {
        Timber.d("navigateToHome()")
        val navOption = NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setPopUpTo(R.id.home_dest, false)
        }

        navController.navigate(R.id.home_dest, null, navOption.build())
    }

    private fun navigateToMyHome() {
        Timber.d("navigateToMyHome()")
        val navOption = NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setPopUpTo(R.id.home_dest, false)
        }
        navController.navigate(R.id.my_home_dest, null, navOption.build())
    }

    override fun initListener() {
        setNavDestinationListener()
    }

    private fun setNavDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainActivityViewModel.setCurrentFragmentName(destinationId = destination.id)
        }
    }

    override fun initObserve() {
        with(mainActivityViewModel) {
            lifecycleScope.launch {
                eventFlow.collect { handleEvent(it) }
            }
            lifecycleScope.launch {
                uiStateFlow.collect { handleUiState(it) }
            }
        }
    }

    override fun init() {
    }

    private fun handleFabAnim(event: Event.FabAnim) {
        Timber.d("handleFabAnim(${event.javaClass.name})")
        val rotation: Float
        val animRes = if (event is Event.FabAnim.Rotate0to405) {
            rotation = 405f
            R.anim.rotate_0_to_360
        } else {
            rotation = 0f
            R.anim.rotate_360_to_0
        }

        with(binding.ivFabBtn) {
            startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity, animRes
                )
            )
            this.rotation = rotation
        }
    }

    private fun handleNavToEditCookie(event: Event.Nav.ToEditCookie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_up
                exit = R.anim.slide_out_down
                popEnter = R.anim.slide_in_up
                popExit = R.anim.slide_out_down
            }
            launchSingleTop = true
        }

        val args = event.editCookie?.let { editCookie ->
            Bundle().apply {
                putParcelable("editCookie", editCookie)
            }
        }

        navController.navigate(R.id.editCookie_dest, args, options)
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

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.ShowDialog -> {
                handleShowDialog(event)
            }
            is Event.Nav -> {
                when (event) {
                    is Event.Nav.To -> {
                        handleNavTo(event.action)
                    }
                    is Event.Nav.Up -> {
                        handleNavUp(event)
                    }
                    is Event.Nav.ToEditCookie -> {
                        handleNavToEditCookie(event)
                    }
                }
            }
            is Event.FabAnim -> {
                handleFabAnim(event)
            }
        }
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

    private fun handleNavUp(event: Event.Nav.Up) {
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

            addBackKeyListener()
        }
    }

    private fun hideProgress() {
        if (isProgressShow()) {
            removeProgressBarView()
            removeBackKeyListener()
        }
    }

    private var onBackPressedCallback: OnBackPressedCallback? = null

    private fun addBackKeyListener() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        onBackPressedCallback?.let { onBackPressedDispatcher.addCallback(this, it) }
    }

    private fun removeBackKeyListener() {
        onBackPressedCallback?.remove()
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
