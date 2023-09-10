package com.shapeide.rasadesa.presenter.mappers

import com.shapeide.rasadesa.presenter.base.source.BaseDataState


inline fun <T> extractDataState(
    data: Result<T>,
    crossinline whenSuccess: () -> BaseDataState,
    crossinline whenFailure: () -> BaseDataState,
    crossinline loading: () -> BaseDataState
): BaseDataState {
    return if (data.isSuccess) {
        whenSuccess()
    } else if (data.isFailure) {
        whenFailure()
    } else {
        loading()
    }
}