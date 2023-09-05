package com.shapeide.rasadesa

import android.app.Application
import com.shapeide.rasadesa.utills.NetworkStateViewModel
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RasaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}