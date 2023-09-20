package com.shapeide.rasadesa.presenter.home.navigator

sealed class HomeNavigator {
    data class NavigateToDetailScreen(val id: String) : HomeNavigator()
    data class NavigateToSearchScreen(val category: Pair<String, String>): HomeNavigator()
    data object NavigateToFavoriteScreen: HomeNavigator()
    data object NavigateToAboutScreen: HomeNavigator()
}