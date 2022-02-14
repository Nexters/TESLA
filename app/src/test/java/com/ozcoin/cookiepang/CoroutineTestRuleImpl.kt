package com.ozcoin.cookiepang

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.reflect.KProperty

@ExperimentalCoroutinesApi
class CoroutineTestRuleImpl : CoroutineTestRule {
    override fun beforeTest(): TestCoroutineDispatcher {
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        return testDispatcher
    }

    override fun afterTest(testCoroutineDispatcher: TestCoroutineDispatcher) {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    override operator fun getValue(nothing: Nothing?, property: KProperty<*>): CoroutineTestRule {
        return this
    }
}
