package com.shapeide.rasadesa.utills

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.databases.category.CategoryRepository
import com.shapeide.rasadesa.databases.meals.MealRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.viewmodels.NetworkStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RasaApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { RoomDB.getInstanceDB(this, applicationScope) }
    private val apiEndpoint by lazy { APIEndpoint.create() }
    val categoryRepository by lazy { CategoryRepository(database, apiEndpoint) }
    val mealRepository by lazy { MealRepository(database.mealDao, apiEndpoint) }

    /* Network State Listener, the second method, IT IS WORKED! */
    val networkStateViewModel: NetworkStateViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
            .create(NetworkStateViewModel::class.java)
    }
}