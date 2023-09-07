package com.shapeide.rasadesa.presenter.home.state

import com.shapeide.rasadesa.presenter.base.StateError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class HomeScreenState(
    val unit: Unit
)