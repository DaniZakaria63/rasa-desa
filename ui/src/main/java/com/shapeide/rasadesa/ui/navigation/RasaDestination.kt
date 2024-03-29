package com.shapeide.rasadesa.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.shapeide.rasadesa.presenter.domain.NavTarget
import com.shapeide.rasadesa.presenter.search.navigator.SearchNavigator
import com.shapeide.rasadesa.ui.navigation.base.BaseDestination


object HomeDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Home
    override val route: String
        get() = NavTarget.Home.label
}

object AboutDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Info
    override val route: String
        get() = NavTarget.About.label
}

object FavoriteDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Favorite
    override val route: String
        get() = NavTarget.Favorite.label
}

object SearchDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Search
    override val route: String
        get() = NavTarget.Search.label

    val searchQueryArgs = SearchNavigator.SEARCH_PARAM_ARGS_QUERY
    val routeWithArgs = "$route?$searchQueryArgs={$searchQueryArgs}"
    val arguments = listOf(
        navArgument(searchQueryArgs) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
    val deepLink = listOf(
        navDeepLink { uriPattern = "rasadesa://$route?$searchQueryArgs={$searchQueryArgs}" }
    )
}

object DetailDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Fastfood
    override val route: String
        get() = NavTarget.Detail.label

    const val detailIdArgs = "recipe_id"
    val routeWithArgs = "$route/{$detailIdArgs}"
    val arguments = listOf(
        navArgument(detailIdArgs) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
    val deepLink = listOf(
        navDeepLink { uriPattern = "rasadesa://$route/{$detailIdArgs}" }
    )
}