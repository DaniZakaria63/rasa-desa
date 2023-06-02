package com.shapeide.rasadesa.utills

import android.app.Application
import com.shapeide.rasadesa.databases.RoomDB
import com.shapeide.rasadesa.networks.APIEndpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RasaApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RoomDB.getInstanceDB(this, applicationScope) }
    val apiEndpoint by lazy { APIEndpoint.create() }
}