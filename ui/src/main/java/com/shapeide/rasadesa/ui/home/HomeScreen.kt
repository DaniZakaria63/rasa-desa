package com.shapeide.rasadesa.ui.home

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.domain.Status
import com.shapeide.rasadesa.presenter.home.navigator.HomeNavigator
import com.shapeide.rasadesa.presenter.home.viewmodel.HomeViewModel
import com.shapeide.rasadesa.presenter.search.navigator.SearchNavigator
import com.shapeide.rasadesa.ui.home.categories.HomeCategory
import com.shapeide.rasadesa.ui.home.list.HomeList
import com.shapeide.rasadesa.ui.navigation.AboutDestination
import com.shapeide.rasadesa.ui.theme.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigatorCallback: (HomeNavigator) -> Unit,
) {
    var loadingIndicator by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getRecipesByMealType()
    }

    val recipeState by viewModel.recipeState.collectAsStateWithLifecycle()
    LaunchedEffect(recipeState.status) {
        Timber.i("Data Status depends: ${recipeState.status} , isLoading = ${recipeState.isLoading}, isEmpty= ${recipeState.recipeList.isNullOrEmpty()}")
        when (recipeState.status) {
            Status.ERROR -> {
                loadingIndicator = false
                Timber.e("Error happened")
            }

            Status.DATA -> {
                loadingIndicator = false
                Timber.d("Everything is setting up")
            }

            Status.LOADING -> {
                loadingIndicator = true
                Timber.d("Loading")
            }
        }
    }

    val navigator by viewModel.navigation.collectAsStateWithLifecycle(initialValue = null)
    LaunchedEffect(navigator) {
        navigator?.let { nav -> navigatorCallback(nav) }
    }

    LazyColumn(modifier = Modifier.padding(horizontal = Dimens.large)) {
        item {
            Row(
                modifier = Modifier
                    .padding(top = Dimens.normal)
                    .padding(
                        vertical = Dimens.large
                    )
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hi! Nice to see you",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.Text.display,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = Dimens.small)
                    )
                    Text(
                        text = "What are you cooking today?",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = Dimens.Text.subtitle,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = Dimens.xsmall)
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.navigateTo(HomeNavigator.NavigateToAboutScreen)
                    }
                ) {
                    Icon(
                        imageVector = AboutDestination.icon,
                        contentDescription = AboutDestination.route,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(Dimens.iconXLarge)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clip(CircleShape)
                            .padding(Dimens.normal)
                    )
                }
            }
        }
        item {
            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium,
                fontSize = Dimens.Text.subtitle,
                modifier = Modifier.padding(vertical = Dimens.medium)
            )
            HomeCategory(
                modifier = Modifier
                    .padding(bottom = Dimens.large)
                    .fillMaxWidth()
            ) {
                coroutineScope.launch {
                    viewModel.navigateTo(
                        HomeNavigator.NavigateToSearchScreen(
                            Pair(
                                SearchNavigator.SEARCH_PARAM_ARGS_QUERY,
                                it.title
                            )
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(Dimens.normal))
        }
        item {
            Column(modifier = Modifier.padding(bottom = Dimens.medium)) {
                Text(
                    text = "Featured Recipes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = Dimens.Text.subtitle,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = Dimens.xsmall)
                )

                Text(
                    text = "Discover recipes with their nutrition",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = Dimens.Text.body,
                    modifier = Modifier.padding(bottom = Dimens.normal)
                )
            }
        }
        item {
            if (loadingIndicator) {
                Column(
                    modifier = Modifier
                        .padding(Dimens.large)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(72.dp),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
        items(recipeState.recipeList ?: listOf()) { recipe: RecipePreview ->
            HomeList(
                recipe,
            ) { recipeID ->
                coroutineScope.launch(Dispatchers.Main) {
                    viewModel.navigateTo(
                        HomeNavigator.NavigateToDetailScreen(
                            recipeID ?: "err#2"
                        )
                    )
                }
            }
        }
    }
}