package com.ozcoin.cookiepang.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpinnerClickListener(
    initialValue: Int,
    private val coroutineScope: CoroutineScope
) {

    private val _numValue = MutableStateFlow(initialValue)
    val numValue: StateFlow<Int>
        get() = _numValue

    fun clickPlus() {
        coroutineScope.launch {
            _numValue.emit(_numValue.value + 1)
        }
    }

    fun clickMinus() {
        coroutineScope.launch {
            val num = if (_numValue.value > 0)
                _numValue.value - 1
            else
                _numValue.value
            _numValue.emit(num)
        }
    }
}
