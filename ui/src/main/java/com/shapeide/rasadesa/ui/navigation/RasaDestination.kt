package com.shapeide.rasadesa.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.shapeide.rasadesa.presenter.domain.NavTarget
import com.shapeide.rasadesa.ui.navigation.base.BaseDestination


object HomeDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Home
    override val route: String
        get() = NavTarget.Home.label
}

object SearchDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Outlined.Search
    override val route: String
        get() = NavTarget.Search.label
}

object DetailDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Fastfood
    override val route: String
        get() = NavTarget.Detail.label

    const val detailIdArgs = "recipe_id"
    val routeWithArgs = "$route/?$detailIdArgs={$detailIdArgs}"
    val arguments = listOf(
        navArgument(detailIdArgs) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
    val deepLink = listOf(
        navDeepLink { uriPattern = "rasadesa://$route/?$detailIdArgs={$detailIdArgs}" }
    )
}