package com.shapeide.rasadesa.presenter.detail.navigator

sealed class DetailNavigator {
    data object NavigateToHomeScreen: DetailNavigator()
}