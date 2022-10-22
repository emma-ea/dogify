package com.emma_ea.dogify.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual fun getDispatcherProvider(): DispatcherProvider =
    AndroidDispatcherProvider()

private class AndroidDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val unconfined = Dispatchers.Unconfined
}