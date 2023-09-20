package com.shapeide.rasadesa.presenter.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipes_favorites.GetRecipesFavoritesInteractor
import com.shapeide.rasadesa.core.interactors.set_recipe_favorite.SetRecipeFavoriteInteractor
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.presenter.favorite.navigator.FavoriteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val getRecipesFavorites: GetRecipesFavoritesInteractor,
    val setRecipeFavorite: SetRecipeFavoriteInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _favoriteDataState: MutableStateFlow<List<RecipePreview>> = MutableStateFlow(
        listOf()
    )
    val favoriteDataState: StateFlow<List<RecipePreview>>
        get() = _favoriteDataState
            .asStateFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get()= _loadingState
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _errorState: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorState: SharedFlow<Throwable> get()= _errorState
        .asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigator: MutableSharedFlow<FavoriteNavigator> = MutableSharedFlow()
    val navigator: SharedFlow<FavoriteNavigator> get()= _navigator
        .asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.Eagerly)

    fun getRecipes() {
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipesFavorites()
                .flowOn(dispatcherProvider.io)
                .collect { result ->
                    if (result.isSuccess) {
                        _loadingState.emit(false)
                        _favoriteDataState.emit(result.getOrNull()?: listOf())
                    } else if (result.isFailure) {
                        _loadingState.emit(false)
                        _errorState.emit(result.exceptionOrNull()?:IllegalStateException("No Such Data"))
                    } else {
                        _loadingState.emit(true)
                    }
                }
        }
    }

    fun updateFavoriteRecipes(recipe: RecipePreview){
        viewModelScope.launch(dispatcherProvider.main) {
            setRecipeFavorite(recipeId = recipe.id?:"err#1", !recipe.isFavorite!!)
            getRecipes()
        }
    }

    fun navigateTo(navigate: FavoriteNavigator){
        viewModelScope.launch(dispatcherProvider.main) {
            _navigator.emit(navigate)
        }
    }
}