package com.emma_ea.dogify

import android.app.Application
import com.emma_ea.dogify.di.initKoin
import org.koin.android.ext.koin.androidContext

class DogifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DogifyApplication)
        }
    }
}