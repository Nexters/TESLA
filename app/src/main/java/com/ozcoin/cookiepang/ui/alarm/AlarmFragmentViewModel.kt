package com.ozcoin.cookiepang.ui.alarm

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.alarm.Alarms
import com.ozcoin.cookiepang.domain.alarm.AlarmsRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.TitleClickListener
import com.ozcoin.cookiepang.utils.observer.EventObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val alarmsRepository: AlarmsRepository
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            loadAlarmsList()
        }
    }

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    private val _alarmsList = MutableStateFlow(emptyList<Alarms>())
    val alarmsList: StateFlow<List<Alarms>>
        get() = _alarmsList

    private suspend fun loadAlarmsList() {
        userRepository.getLoginUser()?.let {
            val result = alarmsRepository.getAlarmsList("dfd")
            if (result is DataResult.OnSuccess)
                _alarmsList.emit(result.response)
        }
    }
}
