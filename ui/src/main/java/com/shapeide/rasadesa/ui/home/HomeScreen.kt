package com.shapeide.rasadesa.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.domain.Status
import com.shapeide.rasadesa.presenter.home.navigator.HomeNavigator
import com.shapeide.rasadesa.presenter.home.viewmodel.HomeViewModel
import com.shapeide.rasadesa.ui.home.categories.HomeCategory
import com.shapeide.rasadesa.ui.home.list.HomeList
import com.shapeide.rasadesa.ui.navigation.SearchDestination
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

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
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
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = Dimens.small)
                        )
                        Text(
                            text = "What are you cooking today?",
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = Dimens.xsmall)
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.navigateTo(HomeNavigator.NavigateToSearchScreen)
                        }
                    ) {
                        Icon(
                            imageVector = SearchDestination.icon,
                            contentDescription = SearchDestination.route,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(Dimens.iconXLarge)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clip(CircleShape)
                                .shadow(
                                    0.5.dp,
                                    shape = CircleShape
                                )
                                .padding(Dimens.normal)
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = Dimens.medium, bottom = Dimens.normal)
                )
                HomeCategory(
                    modifier = Modifier
                        .padding(bottom = Dimens.large)
                        .fillMaxWidth()
                )
            }
            item {
                Column(modifier = Modifier.padding(bottom = Dimens.medium)) {
                    Text(
                        text = "Featured Recipes",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = Dimens.xsmall)
                    )

                    Text(
                        text = "Discover recipes with their nutrition",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = Dimens.normal)
                    )
                }
            }
            item {
                if (loadingIndicator) CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimens.xlarge),
                    color = MaterialTheme.colorScheme.inverseSurface,
                    strokeWidth = Dimens.normal
                )
            }
            items(recipeState.recipeList ?: listOf()) { recipe: RecipePreview ->
                HomeList(recipe, onClicked = { recipeID ->
                    coroutineScope.launch(Dispatchers.Main) {
                        viewModel.navigateTo(
                            HomeNavigator.NavigateToDetailScreen(
                                recipeID ?: "err#2"
                            )
                        )
                    }
                })
            }
        }
    }
}