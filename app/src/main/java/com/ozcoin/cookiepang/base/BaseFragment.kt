package com.ozcoin.cookiepang.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun initView()

    protected abstract fun initListener()

    protected abstract fun initObserve()

    protected abstract fun init()

    protected val binding: T
        get() = _binding!!

    private var _binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val localInflater = inflater.cloneInContext(
            ContextThemeWrapper(
                requireActivity(),
                R.style.Base_Theme_CookiePang
            )
        )
        _binding = DataBindingUtil.inflate(localInflater, getLayoutRes(), container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner

        _binding?.root?.background =
            ContextCompat.getDrawable(inflater.context, R.drawable.bg_gradient_basic_black)
        return _binding?.root
    }

    val viewLifecycleScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycleScope

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initObserve()
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun observeEvent(viewModel: BaseViewModel) {
        viewLifecycleScope.launch {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    protected fun animSlideUpContents() {
        with(binding.root) {
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_up))
        }
    }

    protected fun handleEvent(event: Event) {
        when (event) {
            is Event.Nav -> {
                when (event) {
                    is Event.Nav.To -> {
                        handleNavTo(event.action)
                    }
                    is Event.Nav.Up -> {
                        handleNavUp(event)
                    }
                    is Event.Nav.ToAlarm -> {
                        navigateToAlarm()
                    }
                    is Event.Nav.ToSetting -> {
                        navigateToSetting()
                    }
                }
            }
            is Event.ShowWeb -> {
                showWeb(event)
            }
            is Event.ShowToast -> {
                showToast(event.msg)
            }
            else -> {}
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun showWeb(event: Event.ShowWeb) {
        kotlin.runCatching {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.url)))
        }.onFailure {
            Timber.e(it)
        }
    }

    private fun navigateToSetting() {
        val options = navOptions {
            anim {
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        findNavController().navigate(R.id.setting_dest, null, options)
    }

    private fun navigateToAlarm() {
        val options = navOptions {
            anim {
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        findNavController().navigate(R.id.alarm_dest, null, options)
    }

    private fun handleNavTo(action: NavDirections) {
        Timber.d("navigate to : ${action.javaClass.simpleName}")
        findNavController().navigate(action)
    }

    private fun handleNavUp(event: Event.Nav.Up) {
        Timber.d("navigate Up")
        if (event.key != null)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                event.key,
                event.value
            )

        findNavController().navigateUp()
    }

    override fun onStart() {
        super.onStart()
        checkBackStack()
    }

    private fun checkBackStack() {
        Timber.d("Fragment Back Stack Cnt: ${parentFragmentManager.backStackEntryCount}")
        for (i in 0 until parentFragmentManager.backStackEntryCount) {
            Timber.d("Fragment Back Stack : ${parentFragmentManager.getBackStackEntryAt(i).name}")
        }
    }
}
