package com.shapeide.rasadesa.presenter.base

abstract class BaseUiState(
    open val isLoading: Boolean = false,
    open val isError: Boolean = false,
) {
    protected val status: Status
        get() = if (isLoading) {
            Status.LOADING
        } else if (isError) {
            Status.ERROR
        } else {
            Status.DATA
        }
}