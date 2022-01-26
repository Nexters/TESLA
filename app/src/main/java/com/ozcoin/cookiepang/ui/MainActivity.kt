package com.ozcoin.cookiepang.ui

import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainVm by stateViewModel<MainActivityViewModel>()
    private lateinit var navController: NavController

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        with(binding) {
            viewModel = mainVm
            navController =
                (supportFragmentManager.findFragmentById(binding.fcvNavHost.id) as NavHostFragment).navController

            setUpActionBar()
            includeBtmNavLayout.customBtmNav.setupWithNavController(navController)
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarTitle)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest, R.id.my_home_dest))
        setupActionBarWithNavController(navController, appBarConfiguration)
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

    }

    override fun init() {

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_title_bar, menu)
        return true
    }
}