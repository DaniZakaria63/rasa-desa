package com.shapeide.rasadesa.presenter.main.state

import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.BaseUiState

data class RecipeUiState(
    var dataList: List<RecipePreview>? = null,
    override var isError: Boolean = false,
    override var isLoading: Boolean = false
) : BaseUiState(isLoading, isError)