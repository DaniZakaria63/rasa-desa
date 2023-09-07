package com.shapeide.rasadesa.presenter.base

import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseDataState(
    open val isLoading: Boolean = false,
    open val isError: Boolean = false,
    open val _eventError: MutableSharedFlow<StateError>?,
) {
    open val status: Status
        get() = if (isError) {
            Status.ERROR
        } else {
            Status.LOADING
        }
}

data class StateError(
    val message: String,
    val type: StateErrorType = StateErrorType.UNKNOWN
)

enum class StateErrorType {
    AUTHENTICATION_ERROR, SERVER_ERROR, CLIENT_ERROR, CELLULAR_ERROR, DATABASE_ERROR, UNKNOWN
}