package com.shapeide.rasadesa.ui.navigation.base

import androidx.compose.ui.graphics.vector.ImageVector

interface BaseDestination {
    val icon: ImageVector
    val route: String
}