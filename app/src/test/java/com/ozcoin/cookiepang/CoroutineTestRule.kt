package com.ozcoin.cookiepang

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.reflect.KProperty

@ExperimentalCoroutinesApi
interface CoroutineTestRule {
    fun beforeTest(): TestCoroutineDispatcher
    fun afterTest(testCoroutineDispatcher: TestCoroutineDispatcher)
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): CoroutineTestRule
}
