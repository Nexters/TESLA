package com.ozcoin.cookiepang.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.EventFlow
import com.ozcoin.cookiepang.utils.MutableEventFlow
import com.ozcoin.cookiepang.utils.asEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _eventFlow = MutableEventFlow<Event>()
    val eventFlow: EventFlow<Event>
        get() = _eventFlow.asEventFlow()

    protected fun navigateTo(action: NavDirections) {
        viewModelScope.launch {
            _eventFlow.emit(
                Event.Nav.To(action)
            )
        }
    }
}
