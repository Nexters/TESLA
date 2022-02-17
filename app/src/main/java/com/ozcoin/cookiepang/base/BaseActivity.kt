package com.ozcoin.cookiepang.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController

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
}
