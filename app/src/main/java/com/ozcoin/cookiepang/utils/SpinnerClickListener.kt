package com.ozcoin.cookiepang.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SpinnerClickListener(
    initialValue: Int,
    private val coroutineScope: CoroutineScope
) {

    val numValue = MutableStateFlow(initialValue.toString())

    fun clickPlus() {
        val num = kotlin.runCatching {
            numValue.value.toInt() + 1
        }.onFailure {
            Timber.e(it)
        }.getOrDefault(0)

        coroutineScope.launch {
            numValue.emit(num.toString())
        }
    }

    fun clickMinus() {
        val num = kotlin.runCatching {
            if (numValue.value.toInt() > 0)
                numValue.value.toInt() - 1
            else
                numValue.value.toInt()
        }.onFailure {
            Timber.e(it)
        }.getOrDefault(0)

        coroutineScope.launch {
            numValue.emit(num.toString())
        }
    }
}
