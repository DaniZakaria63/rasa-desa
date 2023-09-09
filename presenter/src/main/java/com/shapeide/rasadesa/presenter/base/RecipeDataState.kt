package com.shapeide.rasadesa.presenter.base

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.source.BaseDataState
import com.shapeide.rasadesa.presenter.base.source.StateError
import com.shapeide.rasadesa.presenter.domain.Status

data class RecipeDataState(
    var recipeList: List<RecipePreview>? = null,
    override val isLoading: Boolean = false,
    override val isError: Boolean = false,
    override val eventError: StateError? = null,
) : BaseDataState(isLoading, isError, eventError){
    override val status: Status
        get() = super.status
}