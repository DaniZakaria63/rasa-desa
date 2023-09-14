package com.shapeide.rasadesa.presenter.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapeide.rasadesa.core.interactors.get_recipes_with_params.GetRecipesWithParamsInteractor
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.domain.source.DispatcherProvider
import com.shapeide.rasadesa.presenter.base.RecipeListDataState
import com.shapeide.rasadesa.presenter.mappers.extractDataState
import com.shapeide.rasadesa.presenter.search.navigator.SearchNavigator
import com.shapeide.rasadesa.presenter.search.state.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val getRecipesWithParams: GetRecipesWithParamsInteractor,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    val _queryListParams: List<MealType> by lazy { MealType.values().toList() }
    private var _queryState: MutableStateFlow<MealType> = MutableStateFlow(MealType.Breakfast)

    val queryState: StateFlow<MealType> get() = _queryState
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, MealType.Breakfast)

    private val _searchDataState: MutableStateFlow<RecipeListDataState> =
        MutableStateFlow(RecipeListDataState(isLoading = true))
    val searchDataState: StateFlow<RecipeListDataState> get() =  _searchDataState
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, RecipeListDataState())

    private val _searchErrorState: MutableSharedFlow<SearchScreenState.SearchError> =
        MutableSharedFlow()

    val searchErrorState: SharedFlow<SearchScreenState.SearchError> get() =
        _searchErrorState.asSharedFlow()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigation: MutableSharedFlow<SearchNavigator> = MutableSharedFlow()
    val navigation get() =  _navigation.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun searchDataByType(type: MealType) {
        viewModelScope.launch(dispatcherProvider.main) {
            getRecipesWithParams(type)
                .flowOn(dispatcherProvider.io)
                .map { data ->
                    extractDataState(
                        data,
                        { RecipeListDataState(recipeList = data.getOrNull()) },
                        { RecipeListDataState(isError = true) },
                        { RecipeListDataState(isLoading = true) }
                    ) as RecipeListDataState
                }.collect {
                    _searchDataState.emit(it)
                }
        }
    }

    fun parseQuery(query: String?) {
        viewModelScope.launch(dispatcherProvider.main) {
            _queryListParams.find { it.name == query }?.also {
                _queryState.emit(it)
                searchDataByType(it)
            } ?: run {
                _searchErrorState.emit(
                    SearchScreenState.SearchError(
                        UnsupportedOperationException(
                            "Not Supported Yet"
                        )
                    )
                )
            }
        }
    }

    fun navigateTo(navigator: SearchNavigator){
        viewModelScope.launch(dispatcherProvider.main) {
            _navigation.emit(navigator)
        }
    }
}