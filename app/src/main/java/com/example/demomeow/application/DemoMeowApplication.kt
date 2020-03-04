package com.example.demomeow.application

import android.app.Application
import com.example.demomeow.module.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoMeowApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Adding koin modules to our application
        startKoin {
            androidContext(this@DemoMeowApplication)
            modules(appModules)
        }
    }
}