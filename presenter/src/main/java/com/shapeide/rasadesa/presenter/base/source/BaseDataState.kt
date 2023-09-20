package com.shapeide.rasadesa.presenter.base.source

import com.shapeide.rasadesa.presenter.domain.Status

abstract class BaseDataState(
    open val isLoading: Boolean = false,
    open val isError: Boolean = false,
    open val eventError: StateError? = null,
) {
    open val status: Status
        get() = if (isError) {
            Status.ERROR
        } else if (isLoading) {
            Status.LOADING
        } else {
            Status.DATA
        }
}

data class StateError(
    val message: String,
    val type: StateErrorType = StateErrorType.UNKNOWN
)

enum class StateErrorType {
    AUTHENTICATION_ERROR, SERVER_ERROR, CLIENT_ERROR, CELLULAR_ERROR, DATABASE_ERROR, UNKNOWN
}