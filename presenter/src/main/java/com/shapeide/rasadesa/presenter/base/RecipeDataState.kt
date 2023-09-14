package com.shapeide.rasadesa.presenter.base

import com.shapeide.rasadesa.domain.domain.Recipe
import com.shapeide.rasadesa.presenter.base.source.BaseDataState
import com.shapeide.rasadesa.presenter.base.source.StateError
import com.shapeide.rasadesa.presenter.domain.Status

data class RecipeDataState(
    var recipe: Recipe? = null,
    override val isError: Boolean = false,
    override val isLoading: Boolean = false,
    override val eventError: StateError? = StateError(message = "")
): BaseDataState(isLoading, isError, eventError) {
    override val status: Status
        get() = super.status
}