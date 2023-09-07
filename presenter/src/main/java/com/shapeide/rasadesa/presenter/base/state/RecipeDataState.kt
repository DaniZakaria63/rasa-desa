package com.shapeide.rasadesa.presenter.base.state

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.BaseDataState
import com.shapeide.rasadesa.presenter.base.StateError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class RecipeDataState(
    var recipeList: List<RecipePreview>? = null,
    override val isLoading: Boolean = false,
    override val isError: Boolean = false,
    override val _eventError: MutableSharedFlow<StateError>? = MutableSharedFlow(),
) : BaseDataState(isLoading, isError, _eventError){
    val eventError get() = _eventError?.asSharedFlow()
}