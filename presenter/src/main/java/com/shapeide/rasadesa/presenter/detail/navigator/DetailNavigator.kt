package com.shapeide.rasadesa.presenter.detail.navigator

sealed class DetailNavigator {
    data object NavigateToHomeScreen: DetailNavigator()
    data class NavigateToAnotherDetailScreen(val id:String): DetailNavigator()
}