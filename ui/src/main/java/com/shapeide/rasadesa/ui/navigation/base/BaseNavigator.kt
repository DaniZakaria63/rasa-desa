package com.shapeide.rasadesa.ui.navigation.base

import kotlinx.coroutines.channels.Channel

interface BaseNavigator {
    val navigation : Channel<BaseDestination>

    suspend fun navigateTo(route: BaseDestination)
}