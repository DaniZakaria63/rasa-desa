package com.shapeide.rasadesa.presenter.mappers

import com.shapeide.rasadesa.presenter.base.source.BaseDataState
import timber.log.Timber


inline fun <T> extractDataState(
    data: Result<T>,
    crossinline whenSuccess: () -> BaseDataState,
    crossinline whenFailure: () -> BaseDataState,
    crossinline loading: () -> BaseDataState
): BaseDataState {
    return if (data.isSuccess) {
        whenSuccess()
    } else if (data.isFailure) {
        Timber.e(data.exceptionOrNull())
        whenFailure()
    } else {
        loading()
    }
}