package com.shapeide.rasadesa.presenter.base.state

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.BaseDataState
import com.shapeide.rasadesa.presenter.base.StateError
import com.shapeide.rasadesa.presenter.base.Status
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class RecipeDataState(
    var recipeList: List<RecipePreview>? = null,
    override val isLoading: Boolean = false,
    override val isError: Boolean = false,
    override val eventError: StateError? = null,
) : BaseDataState(isLoading, isError, eventError){
    override val status: Status
        get() = super.status
}