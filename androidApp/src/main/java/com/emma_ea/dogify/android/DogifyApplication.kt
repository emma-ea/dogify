package com.emma_ea.dogify.android

import android.app.Application
import com.emma_ea.dogify.android.di.viewModelModule
import com.emma_ea.dogify.di.initKoin
import org.koin.android.ext.koin.androidContext

class DogifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DogifyApplication)
            modules(viewModelModule)
        }
    }
}