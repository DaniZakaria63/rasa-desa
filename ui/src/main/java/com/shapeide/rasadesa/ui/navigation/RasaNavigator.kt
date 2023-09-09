package com.shapeide.rasadesa.ui.navigation


import com.shapeide.rasadesa.ui.navigation.base.BaseDestination
import com.shapeide.rasadesa.ui.navigation.base.BaseNavigator
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

class RasaNavigator: BaseNavigator {
    override val navigation: Channel<BaseDestination>
        get() = Channel(
            onBufferOverflow = BufferOverflow.DROP_LATEST
        )

    override suspend fun navigateTo(route: BaseDestination) {
        navigation.send(route)
    }
}