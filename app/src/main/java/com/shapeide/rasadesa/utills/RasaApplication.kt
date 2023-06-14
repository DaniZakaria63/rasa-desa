package com.shapeide.rasadesa.utills

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.meals.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.viewmodels.NetworkStateViewModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class RasaApplication : Application() {
    /* Network State Listener, the second method, IT IS WORKED! */
    val networkStateViewModel: NetworkStateViewModel by lazy {
        NetworkStateViewModel(this)
    }
}