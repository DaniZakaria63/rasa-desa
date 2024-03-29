package com.shapeide.rasadesa.presenter.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipe_by_id.GetRecipeDetailByIdInteractor
import com.shapeide.rasadesa.core.interactors.get_recipe_favorite_by_id.GetRecipeFavoriteByIdInteractor
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
import com.shapeide.rasadesa.core.interactors.set_recipe_favorite.SetRecipeFavoriteInteractor
import com.shapeide.rasadesa.domain.domain.Ingredients
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.domain.domain.NutrientsSub
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.presenter.base.RecipeDataState
import com.shapeide.rasadesa.presenter.base.RecipeListDataState
import com.shapeide.rasadesa.presenter.detail.navigator.DetailNavigator
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState
import com.shapeide.rasadesa.presenter.detail.state.DetailTabState
import com.shapeide.rasadesa.presenter.domain.DetailTab
import com.shapeide.rasadesa.presenter.mappers.extractDataState
import com.shapeide.rasadesa.presenter.mappers.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getRecipeDetailById: GetRecipeDetailByIdInteractor,
    val getRecipesWithParams: GetRecipesWithParamsInteractor,
    val getRecipeFavoriteById: GetRecipeFavoriteByIdInteractor,
    val setRecipeFavorite: SetRecipeFavoriteInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private var recipeIdKey: String = "initial"

    private val _navigatorDetail: MutableSharedFlow<DetailNavigator> =
        MutableSharedFlow()
    val navigatorDetail
        get() = _navigatorDetail.asSharedFlow()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _recipeState: MutableStateFlow<RecipeDataState> =
        MutableStateFlow(RecipeDataState(isLoading = true))

    private val _recipesOtherState: MutableStateFlow<RecipeListDataState> =
        MutableStateFlow(RecipeListDataState(isLoading = true))

    private var isRecipeFavorite: Boolean = false
    private val _favoriteStatus: MutableStateFlow<Boolean> = MutableStateFlow(isRecipeFavorite)
    val favoriteStatus: StateFlow<Boolean> = _favoriteStatus.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), isRecipeFavorite)

    val detailScreenState: StateFlow<DetailScreenState> =
        combine(_recipeState, _recipesOtherState) { state, other ->
            val newState = state.toState()
            newState.moreRecipes = other.recipeList
            newState
        }.flowOn(dispatcherProvider.io)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DetailScreenState())

    private val _tabState: MutableStateFlow<DetailTabState> =
        MutableStateFlow(DetailTabState())

    val tabState: StateFlow<DetailTabState> = _tabState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DetailTabState())

    fun getDetail(id: String?) {
        recipeIdKey = id.toString()
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipeDetailById(id ?: "null")
                .flowOn(dispatcherProvider.main)
                .map { data ->
                    extractDataState(
                        data,
                        { RecipeDataState(recipe = data.getOrNull()) },
                        { RecipeDataState(isError = true) },
                        { RecipeDataState(isLoading = true) }
                    ) as RecipeDataState
                }.collect {
                    _recipeState.emit(it)
                }

            launch(dispatcherProvider.main) {
                getRecipesWithParams(MealType.Breakfast)
                    .flowOn(dispatcherProvider.main)
                    .map { data ->
                        extractDataState(
                            data,
                            { RecipeListDataState(recipeList = data.getOrNull()) },
                            { RecipeListDataState(isError = true) },
                            { RecipeListDataState(isLoading = true) }
                        ) as RecipeListDataState
                    }
                    .collect {
                        _recipesOtherState.emit(it)
                    }
            }.join()
        }
    }

    fun updateTabPosition(state: DetailTab) {
        viewModelScope.launch(dispatcherProvider.main) {
            _tabState.emit(DetailTabState(tabActiveState = state))
        }
    }

    fun navigateTo(navigator: DetailNavigator) {
        viewModelScope.launch(dispatcherProvider.main) {
            _navigatorDetail.emit(navigator)
        }
    }

    fun getFavoriteStatus() {
        viewModelScope.launch(dispatcherProvider.main) {
            val isFavorite = getRecipeFavoriteById(recipeIdKey)
            Timber.i("GetFavoriteStatus: Favorite Recipe Status: $isFavorite")
            isRecipeFavorite = isFavorite
            _favoriteStatus.emit(isFavorite)
        }
    }

    fun addToFavorite() {
        viewModelScope.launch(dispatcherProvider.main) {
            val recipe = setRecipeFavorite(recipeIdKey, !isRecipeFavorite)
            Timber.i("AddToFavorite: Favorite Recipe Status: $recipe")
            getFavoriteStatus()
        }
    }
}
