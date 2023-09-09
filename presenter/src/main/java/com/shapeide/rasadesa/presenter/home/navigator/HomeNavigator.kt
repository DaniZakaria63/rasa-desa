package com.shapeide.rasadesa.presenter.home.navigator

sealed class HomeNavigator {
    data class NavigateToDetailScreen(val id: String) : HomeNavigator()
    data object NavigateToSearchScreen: HomeNavigator()
}