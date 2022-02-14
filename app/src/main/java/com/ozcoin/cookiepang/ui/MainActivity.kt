package com.ozcoin.cookiepang.ui

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivityMainBinding
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
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d("destination : ${destination.navigatorName}")

            supportFragmentManager.fragments.forEachIndexed { index, fragment ->
                Timber.d("$index : ${fragment.javaClass.simpleName}")
            }
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
}
