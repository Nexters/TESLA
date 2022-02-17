package com.ozcoin.cookiepang.base

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
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

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val localInflater = inflater.cloneInContext(ContextThemeWrapper(requireActivity(), R.style.Base_Theme_CookiePang))
        binding = DataBindingUtil.inflate(localInflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
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
                        handleNavUp()
                    }
                }
            }
            else -> {}
        }
    }

    private fun handleNavTo(action: NavDirections) {
        Timber.d("navigate to : ${action.javaClass.simpleName}")
        findNavController().navigate(action)
    }

    private fun handleNavUp() {
        Timber.d("navigate Up")
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
