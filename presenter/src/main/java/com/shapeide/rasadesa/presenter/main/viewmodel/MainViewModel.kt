package com.shapeide.rasadesa.presenter.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
import com.shapeide.rasadesa.domain.coroutines.DispatcherProvider
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.presenter.main.state.RecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getRecipesWithParams: GetRecipesWithParamsInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _recipesState: MutableStateFlow<RecipeUiState> =
        MutableStateFlow(RecipeUiState(isLoading = true))

    val recipeState: StateFlow<RecipeUiState> = _recipesState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, RecipeUiState())

    fun getRecipesByMealType(mealType: MealType = MealType.Breakfast) {
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipesWithParams(mealType)
                .flowOn(dispatcherProvider.io)
                .map { data ->
                    if (data.isSuccess) {
                        RecipeUiState(dataList = data.getOrNull())
                    } else if (data.isFailure) {
                        RecipeUiState(isError = true)
                    } else {
                        RecipeUiState(isLoading = true)
                    }
                }
                .collect {
                    _recipesState.emit(it)
                }
        }
    }
}