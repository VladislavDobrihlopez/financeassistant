package com.dobrihlopez.financeassistant.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface CoroutineDispatchers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher

    class DefaultCoroutineDispatchers
        @Inject
        constructor() : CoroutineDispatchers {
            override val io: CoroutineDispatcher = Dispatchers.IO
            override val default: CoroutineDispatcher = Dispatchers.Default
            override val main: CoroutineDispatcher = Dispatchers.Main
            override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
        }
}
