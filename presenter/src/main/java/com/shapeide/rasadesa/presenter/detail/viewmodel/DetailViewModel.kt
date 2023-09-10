package com.shapeide.rasadesa.presenter.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipe_by_id.GetRecipeDetailByIdInteractor
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.presenter.base.RecipeDataState
import com.shapeide.rasadesa.presenter.base.RecipeListDataState
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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getRecipeDetailById: GetRecipeDetailByIdInteractor,
    val getRecipesWithParams: GetRecipesWithParamsInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _recipeState: MutableStateFlow<RecipeDataState> =
        MutableStateFlow(RecipeDataState(isLoading = true))

    private val _recipesOtherState: MutableStateFlow<RecipeListDataState> =
        MutableStateFlow(RecipeListDataState(isLoading = true))

    // TODO: Join other state recipes flow
    val detailScreenState: SharedFlow<DetailScreenState> get() =
        _recipeState
            .asSharedFlow()
            .map { it.toState() }
            .flowOn(dispatcherProvider.main)
            .stateIn(viewModelScope, SharingStarted.Eagerly, DetailScreenState())

    private val _tabState: MutableSharedFlow<DetailTabState> =
        MutableSharedFlow()

    val tabState: SharedFlow<DetailTabState> = _tabState.asSharedFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, DetailTabState())

    fun getDetail(id: String?) {
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipeDetailById(id ?: "null")
                .flowOn(dispatcherProvider.main)
                .map { data ->
//                    extractDataState(
//                        data,
//                        { RecipeDataState(recipe = data.getOrNull()) },
//                        { RecipeDataState(isError = true) },
//                        { RecipeDataState(isLoading = true) }
//                    ) as RecipeDataState
                    if (data.isSuccess) {
                        RecipeDataState(recipe = data.getOrNull())
                    } else if (data.isFailure) {
                        Timber.e(data.exceptionOrNull())
                        RecipeDataState(isError = true)
                    } else {
                        RecipeDataState(isLoading = true)
                    }
                }.collect {
                    Timber.i("getRecipeDetailsById returns data: ${it.recipe} | Status: ${it.status}")
                    _recipeState.emit(it)
                }

            launch(dispatcherProvider.main) {
                getRecipesWithParams(MealType.Breakfast)
                    .flowOn(dispatcherProvider.io)
                    .map { data ->
                        RecipeListDataState(recipeList = data.getOrNull())
                    }
                    .collect {
                        Timber.i("getRecipeWithParams returns data: ${it.status}")
                        _recipesOtherState.emit(it)
                    }
            }.join()
        }
    }

    fun updateTabPosition(state: DetailTab){
        viewModelScope.launch(dispatcherProvider.main) {
            _tabState.emit(DetailTabState(tabActiveState = state))
        }
    }
}
