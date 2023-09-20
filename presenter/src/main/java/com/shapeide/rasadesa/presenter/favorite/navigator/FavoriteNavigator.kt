package com.shapeide.rasadesa.presenter.favorite.navigator

sealed class FavoriteNavigator {
    data object NavigateToHomeScreen: FavoriteNavigator()
    data class NavigateToDetailScreen(val detailId: String): FavoriteNavigator()
    data object Default: FavoriteNavigator()
}