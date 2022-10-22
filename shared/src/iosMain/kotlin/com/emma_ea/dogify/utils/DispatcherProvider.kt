package com.emma_ea.dogify.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual fun getDispatcherProvider(): DispatcherProvider =
    IosDispatcherProvider()

private class IosDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.Default
    override val main = Dispatchers.Main
    override val unconfined = Dispatchers.Unconfined
}