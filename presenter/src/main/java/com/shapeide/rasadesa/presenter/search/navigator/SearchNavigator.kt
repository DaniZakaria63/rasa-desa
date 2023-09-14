package com.shapeide.rasadesa.presenter.search.navigator


sealed class SearchNavigator {

    data object NavigateToHomeScreen: SearchNavigator()
    data class NavigateToDetailScreen(val id: String): SearchNavigator()

    companion object{
        @JvmStatic
        val SEARCH_PARAM_ARGS_QUERY = "q"
    }
}