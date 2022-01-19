package com.ozcoin.cookiepang.base

import androidx.lifecycle.ViewModel
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.EventFlow
import com.ozcoin.cookiepang.utils.MutableEventFlow
import com.ozcoin.cookiepang.utils.asEventFlow

abstract class BaseViewModel : ViewModel() {

    protected val _eventFlow = MutableEventFlow<Event>()
    val eventFlow : EventFlow<Event>
        get() = _eventFlow.asEventFlow()

}