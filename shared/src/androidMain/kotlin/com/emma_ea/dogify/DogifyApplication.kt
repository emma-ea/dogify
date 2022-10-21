package com.emma_ea.dogify

import android.app.Application
import com.emma_ea.dogify.di.initKoin

class DogifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}