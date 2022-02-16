package com.ozcoin.cookiepang.ui

import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivityMainBinding
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

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
            lifecycleScope.launchWhenCreated {
                uiStateFlow.collect { handleUiState(it) }
            }
        }
    }

    override fun init() {
    }

    override fun handleFabAnim(event: Event.FabAnim) {
        Timber.d("handleFabAnim(${event.javaClass.name})")
        val animRes = if (event is Event.FabAnim.Left) {
            binding.ivFabBtn.rotation = 405f
            R.anim.rotate_0_to_405
        } else {
            binding.ivFabBtn.rotation = 0f
            R.anim.rotate_405_to_0
        }

        binding.ivFabBtn.startAnimation(
            AnimationUtils.loadAnimation(
                this, animRes
            )
        )
    }

    override fun handleNavToEditCookie() {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_up
                exit = R.anim.slide_out_down
                popEnter = R.anim.slide_in_up
                popExit = R.anim.slide_out_down
            }
        }
        navController.navigate(R.id.editCookie_dest, null, options)
    }
}
