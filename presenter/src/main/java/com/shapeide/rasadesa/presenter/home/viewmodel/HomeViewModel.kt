package com.shapeide.rasadesa.presenter.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipe_favorite_by_id.GetRecipeFavoriteByIdInteractor
import com.shapeide.rasadesa.core.interactors.get_recipes_favorites.GetRecipesFavoritesInteractor
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
import com.shapeide.rasadesa.core.interactors.set_caching_recipes.SetCachingRecipesListInteractor
import com.shapeide.rasadesa.core.interactors.set_recipe_favorite.SetRecipeFavoriteInteractor
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.presenter.base.RecipeListDataState
import com.shapeide.rasadesa.presenter.home.navigator.HomeNavigator
import com.shapeide.rasadesa.presenter.mappers.extractDataState
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
class HomeViewModel @Inject constructor(
    val getRecipesWithParams: GetRecipesWithParamsInteractor,
    val setCachingRecipesList: SetCachingRecipesListInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _recipesState: MutableStateFlow<RecipeListDataState> =
        MutableStateFlow(RecipeListDataState(isLoading = true))

    val recipeState: StateFlow<RecipeListDataState> = _recipesState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, RecipeListDataState())

    private val _navigation = MutableSharedFlow<HomeNavigator>()
    val navigation get() = _navigation.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun getRecipesByMealType(mealType: MealType = MealType.Breakfast) {
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipesWithParams(mealType)
                .flowOn(dispatcherProvider.main)
                .map { data ->
                    extractDataState(
                        data,
                        {RecipeListDataState(recipeList = data.getOrNull())},
                        {RecipeListDataState(isError = true)},
                        {RecipeListDataState(isLoading = true)}
                    ) as RecipeListDataState
                }
                .collect {
                    _recipesState.emit(it)
                    if(it.recipeList != null) setCachingRecipesList(it.recipeList!!)
                }
        }
    }

    fun navigateTo(navigator: HomeNavigator){
        viewModelScope.launch (dispatcherProvider.main){
            _navigation.emit(navigator)
        }
    }
}