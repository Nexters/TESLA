package com.ozcoin.cookiepang.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivityMainBinding
import com.ozcoin.cookiepang.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

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
        }
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
            lifecycleScope.launch {
                mainEventFlow.collect {
                    if (it is MainEvent.FabAnim)
                        handleFabAnim(it)
                    else if (it is MainEvent.NavigateToEditCookie)
                        handleNavToEditCookie(it)
                }
            }
        }
    }

    override fun init() {
    }

    private fun handleFabAnim(event: MainEvent.FabAnim) {
        Timber.d("handleFabAnim(${event.javaClass.name})")
        val rotation: Float
        val animRes = if (event is MainEvent.FabAnim.Rotate0to405) {
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

    private fun handleNavToEditCookie(event: MainEvent.NavigateToEditCookie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_up
                exit = R.anim.slide_out_down
                popEnter = R.anim.slide_in_up
                popExit = R.anim.slide_out_down
            }
        }

        val args = event.editCookie?.let { editCookie ->
            Bundle().also {
                it.putParcelable("editCookie", editCookie)
            }
        }

        navController.navigate(R.id.editCookie_dest, args, options)
    }
}
