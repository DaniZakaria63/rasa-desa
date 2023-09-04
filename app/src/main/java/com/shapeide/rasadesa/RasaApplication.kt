package com.shapeide.rasadesa

import android.app.Application
import com.shapeide.rasadesa.utills.NetworkStateViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RasaApplication : Application() {
    /* Network State Listener, the second method, IT IS WORKED! */
    val networkStateViewModel: NetworkStateViewModel by lazy {
        NetworkStateViewModel(this)
    }
}