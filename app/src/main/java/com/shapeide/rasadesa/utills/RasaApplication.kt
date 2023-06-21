package com.shapeide.rasadesa.utills

import android.app.Application
import com.shapeide.rasadesa.viewmodels.NetworkStateViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RasaApplication : Application() {
    /* Network State Listener, the second method, IT IS WORKED! */
    val networkStateViewModel: NetworkStateViewModel by lazy {
        NetworkStateViewModel(this)
    }
}