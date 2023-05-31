package com.shapeide.rasadesa.utills

import android.app.Application
import com.shapeide.rasadesa.local.RoomDB
import com.shapeide.rasadesa.local.RoomRepository
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RasaApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RoomDB.getInstanceDB(this, applicationScope) }
    val apiEndpoint by lazy { APIEndpoint.create() }
}