package com.emma_ea.dogify.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

}

internal expect fun getDispatcherProvider(): DispatcherProvider