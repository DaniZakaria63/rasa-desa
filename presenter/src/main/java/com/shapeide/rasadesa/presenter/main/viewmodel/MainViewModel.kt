package com.shapeide.rasadesa.presenter.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
import com.shapeide.rasadesa.domain.coroutines.DispatcherProvider
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.state.RecipeDataState
import com.shapeide.rasadesa.presenter.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getRecipesWithParams: GetRecipesWithParamsInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _homeScreenState = MutableSharedFlow<HomeScreenState>()
    val homeScreenState get() = _homeScreenState.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _recipesState: MutableStateFlow<RecipeDataState> =
        MutableStateFlow(RecipeDataState(isLoading = true))

    val recipeState: StateFlow<RecipeDataState> = _recipesState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, RecipeDataState())

    val recipeDummy: StateFlow<RecipeDataState> = MutableStateFlow(
        RecipeDataState(recipeList = listOf(
        RecipePreview(image = "https://dummyimage.com/300", label = "One", dietLabels = listOf("Vegan", "Vegetarian")),
        RecipePreview(image = "https://dummyimage.com/300", label = "Two", dietLabels = listOf("Meat", "Meatball")),
        RecipePreview(image = "https://dummyimage.com/300", label = "Three", dietLabels = listOf("Strict", "Vegan")),
    ))
    )
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), RecipeDataState())

    fun getRecipesByMealType(mealType: MealType = MealType.Breakfast) {
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipesWithParams(mealType)
                .flowOn(dispatcherProvider.io)
                .map { data ->
                    if (data.isSuccess) {
                        RecipeDataState(recipeList = data.getOrNull())
                    } else if (data.isFailure) {
                        RecipeDataState(isError = true)
                    } else {
                        RecipeDataState(isLoading = true)
                    }
                }
                .collect {
                    _recipesState.emit(it)
                }
        }
    }
}