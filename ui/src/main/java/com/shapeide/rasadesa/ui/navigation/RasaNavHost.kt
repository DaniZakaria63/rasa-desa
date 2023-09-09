package com.shapeide.rasadesa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shapeide.rasadesa.presenter.home.navigator.HomeNavigator
import com.shapeide.rasadesa.ui.detail.DetailScreen
import com.shapeide.rasadesa.ui.home.HomeScreen
import com.shapeide.rasadesa.ui.search.SearchScreen
import timber.log.Timber


@Composable
fun RasaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen {
                when (it) {
                    is HomeNavigator.NavigateToSearchScreen -> {
                        navController.navigate(SearchDestination.route)
                    }

                    is HomeNavigator.NavigateToDetailScreen -> {
                        navController.navigate("${DetailDestination.route}/${it.id}") {
                            launchSingleTop = true
                        }
                    }

                    else -> {
                        Timber.e(IllegalAccessException("What??? How???"))
                    }
                }
            }
        }
        composable(route = SearchDestination.route) {
            SearchScreen()
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = DetailDestination.arguments,
            deepLinks = DetailDestination.deepLink
        ) { navBackStackEntry ->
            val recipeID = navBackStackEntry.arguments?.getString(DetailDestination.detailIdArgs)
            DetailScreen(recipe_id = recipeID)
        }
    }
}