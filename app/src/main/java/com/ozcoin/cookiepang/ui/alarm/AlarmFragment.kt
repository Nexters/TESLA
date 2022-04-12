package com.ozcoin.cookiepang.ui.alarm

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.adapter.AlarmsListAdapter
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentAlarmBinding
import com.ozcoin.cookiepang.domain.alarm.AlarmType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>() {

    private val alarmFragmentViewModel by viewModels<AlarmFragmentViewModel>()
    private lateinit var alarmsListAdapter: AlarmsListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_alarm
    }

    override fun initView() {
        with(binding) {
            pageName = "알람"
            titleClickListener = alarmFragmentViewModel.titleClickListener
        }
        setupAlarmsList()
    }

    private fun setupAlarmsList() {
        with(binding.rvAlarms) {
            alarmsListAdapter = AlarmsListAdapter().apply {
                onItemClick = { alarm ->
                    when (alarm.type) {
                        AlarmType.SALE -> findNavController().navigate(AlarmFragmentDirections.actionCookieDetail(alarm.cookieId))
                        AlarmType.ASK -> findNavController().navigate(AlarmFragmentDirections.actionMyHome(isAskRequested = true))
                    }
                }
            }
            adapter = alarmsListAdapter
        }
    }

    override fun initListener() {
    }

    override fun initObserve() {
        observeEvent(alarmFragmentViewModel)
        observeAlarmsList()
    }

    private fun observeAlarmsList() {
        viewLifecycleScope.launch {
            alarmFragmentViewModel.alarmsList.collect {
                alarmsListAdapter.updateList(it)
            }
        }
    }

    override fun init() {
    }
}
