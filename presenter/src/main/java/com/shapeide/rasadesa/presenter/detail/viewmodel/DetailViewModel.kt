package com.shapeide.rasadesa.presenter.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipe_by_id.GetRecipeDetailByIdInteractor
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
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
import kotlinx.coroutines.flow.asSharedFlow
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
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    val nutrientKeyList: List<String> by lazy { extractKeyList() }

    private val _navigatorDetail: MutableSharedFlow<DetailNavigator> =
        MutableSharedFlow()
    val navigatorDetail get() = _navigatorDetail.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _recipeState: MutableStateFlow<RecipeDataState> =
        MutableStateFlow(RecipeDataState(isLoading = true))

    private val _recipesOtherState: MutableStateFlow<RecipeListDataState> =
        MutableStateFlow(RecipeListDataState(isLoading = true))

    val detailScreenState: SharedFlow<DetailScreenState> get() =
        _recipeState
            .asSharedFlow()
            .map { it.toState() }
            .flowOn(dispatcherProvider.main)
            .combine(_recipesOtherState){ state, recipe ->
                state.moreRecipes = recipe.recipeList
                state
            }
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _tabState: MutableSharedFlow<DetailTabState> =
        MutableSharedFlow()

    val tabState: SharedFlow<DetailTabState> = _tabState.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun getDetail(id: String?) {
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
                    .flowOn(dispatcherProvider.io)
                    .map { data ->
                        extractDataState(
                            data,
                            { RecipeListDataState(recipeList = data.getOrNull()) },
                            { RecipeListDataState(isError = true) },
                            { RecipeListDataState(isLoading = true)}
                        ) as RecipeListDataState
                    }
                    .collect {
                        _recipesOtherState.emit(it)
                    }
            }.join()
        }
    }

    fun extractKeyList(): List<String>{
        return Nutrients::class.primaryConstructor!!.parameters.map { it.name?:"-" }
    }

    fun extractValueList(nutrients: Nutrients): List<NutrientsSub>{
        val list :MutableList<NutrientsSub> = mutableListOf()
        try {
            nutrientKeyList.forEach { nutrient ->
                val value = nutrients::class.memberProperties.first { it.name == nutrient }
                list.add(value.getter.call(nutrients) as NutrientsSub)
            }
        }catch(e: Exception){
            Timber.d("Error Nutrient Key List: $nutrientKeyList => $nutrients")
            Timber.e(e)
        }
        return list
    }

    fun updateTabPosition(state: DetailTab){
        viewModelScope.launch(dispatcherProvider.main) {
            _tabState.emit(DetailTabState(tabActiveState = state))
        }
    }

    fun navigateTo(navigator: DetailNavigator){
        viewModelScope.launch(dispatcherProvider.main) {
            _navigatorDetail.emit(navigator)
        }
    }

    //  Not implemented yet
    fun addToFavorite(

    ){

    }
}
