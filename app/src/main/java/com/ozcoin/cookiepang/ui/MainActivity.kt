package com.ozcoin.cookiepang.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.Placeholder
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity
import com.ozcoin.cookiepang.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainVm by viewModel<MainVm>()
    private lateinit var navController: NavController

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserve()
        initListener()
        init()
    }

    private fun initView() {
        with(binding) {
            viewModel = mainVm
            navController =
                (supportFragmentManager.findFragmentById(binding.fcvNavHost.id) as NavHostFragment).navController

            setSupportActionBar(binding.toolbarTitle)
            setupActionBarWithNavController(navController)
            includeBtmNavLayout.customBtmNav.setupWithNavController(navController)
        }
    }

    private fun initListener() {
        setNavDestinationListener()

    }

    private fun setNavDestinationListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d("destination : ${destination.navigatorName}")

        }
    }

    private fun initObserve() {

    }

    private fun init() {
        animContents()
    }

    private fun animContents() {
        with(binding.fcvNavHost) {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_in_up))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_title_bar, menu)
        return true
    }
}