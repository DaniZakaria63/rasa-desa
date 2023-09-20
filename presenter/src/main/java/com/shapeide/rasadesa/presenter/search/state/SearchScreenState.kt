package com.shapeide.rasadesa.presenter.search.state

import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.presenter.base.RecipeListDataState

data class SearchScreenState(
    val queryListParam: List<MealType> = MealType.values().toList(),
    var queryState: MealType? = MealType.Breakfast,
    var dataState: RecipeListDataState = RecipeListDataState(isLoading = true),
    var errorState: SearchError? = SearchError()
) {
    data class SearchError(
        var throwable: Throwable? = null
    ){
        val message: String? get() = throwable?.message
    }
}