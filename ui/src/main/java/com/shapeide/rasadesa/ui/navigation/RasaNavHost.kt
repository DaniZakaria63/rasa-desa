package com.shapeide.rasadesa.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shapeide.rasadesa.presenter.detail.navigator.DetailNavigator
import com.shapeide.rasadesa.presenter.favorite.navigator.FavoriteNavigator
import com.shapeide.rasadesa.presenter.home.navigator.HomeNavigator
import com.shapeide.rasadesa.presenter.search.navigator.SearchNavigator
import com.shapeide.rasadesa.ui.about.AboutScreen
import com.shapeide.rasadesa.ui.detail.DetailScreen
import com.shapeide.rasadesa.ui.favorite.FavoriteScreen
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
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(800, easing = EaseIn)
            ) + fadeIn(animationSpec = tween(800, easing = LinearEasing))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(900, easing = EaseOut)
            ) + fadeOut(animationSpec = tween(900, easing = LinearEasing))
        }
    ) {
        composable(
            route = HomeDestination.route,
        ) {
            HomeScreen {
                when (it) {
                    is HomeNavigator.NavigateToSearchScreen -> {
                        navController.navigateToSearch(it.category)
                    }

                    is HomeNavigator.NavigateToDetailScreen -> {
                        navController.navigateToDetail(it.id)
                    }

                    is HomeNavigator.NavigateToAboutScreen -> {
                        navController.navigateSingleTopTo(AboutDestination.route)
                    }

                    is HomeNavigator.NavigateToFavoriteScreen -> {
                        navController.navigateSingleTopTo(FavoriteDestination.route)
                    }

                    else -> {
                        Timber.e(IllegalAccessException("What??? How???"))
                    }
                }
            }
        }
        composable(
            route = AboutDestination.route,
        ) {
            AboutScreen()
        }
        composable(
            route = FavoriteDestination.route,
        ) {
            FavoriteScreen {
                when (it) {
                    is FavoriteNavigator.NavigateToDetailScreen -> navController.navigateToDetail(it.detailId)
                    is FavoriteNavigator.NavigateToHomeScreen -> navController.navigateSingleTopTo(
                        HomeDestination.route
                    )

                    else -> Timber.e(IllegalAccessException("How???"))
                }
            }
        }
        composable(
            route = SearchDestination.routeWithArgs,
            arguments = SearchDestination.arguments,
            deepLinks = SearchDestination.deepLink,
        ) { navBackEntry ->
            val query = navBackEntry.arguments?.getString(SearchDestination.searchQueryArgs)
            SearchScreen(query) {
                when (it) {
                    is SearchNavigator.NavigateToHomeScreen -> navController.popBackStack()
                    is SearchNavigator.NavigateToDetailScreen -> navController.navigateToDetail(it.id)
                    else -> Timber.e(IllegalAccessException("How could be???"))
                }
            }
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = DetailDestination.arguments,
            deepLinks = DetailDestination.deepLink,
        ) { navBackStackEntry ->
            val recipeID = navBackStackEntry.arguments?.getString(DetailDestination.detailIdArgs)
            DetailScreen(recipe_id = recipeID) {
                when (it) {
                    is DetailNavigator.NavigateToHomeScreen -> navController.popBackStack()
                    is DetailNavigator.NavigateToAnotherDetailScreen -> {
                        navController.navigateToDetail(it.id)
                    }

                    else -> Timber.e(IllegalAccessException("Detail route error, HOW???"))
                }
            }
        }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

private fun NavHostController.navigateToDetail(id: String) =
    this.navigateSingleTopTo("${DetailDestination.route}/$id")

private fun NavHostController.navigateToSearch(query: Pair<String, String>) =
    this.navigateSingleTopTo("${SearchDestination.route}?${query.first}=${query.second}")