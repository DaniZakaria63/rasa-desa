package com.shapeide.rasadesa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.domain.NavTarget
import com.shapeide.rasadesa.presenter.domain.Status
import com.shapeide.rasadesa.presenter.home.navigator.HomeNavigator
import com.shapeide.rasadesa.presenter.home.viewmodel.HomeViewModel
import com.shapeide.rasadesa.ui.R
import com.shapeide.rasadesa.ui.home.categories.HomeCategory
import com.shapeide.rasadesa.ui.home.list.HomeList
import com.shapeide.rasadesa.ui.navigation.SearchDestination
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigatorCallback: (HomeNavigator) -> Unit,
) {
    var loadingIndicator by remember { mutableStateOf(true) }

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
    LaunchedEffect(navigator){
        navigator?.let{ navigatorCallback(it) }
    }

    Surface {
        LazyColumn {
            item {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.dummy),
                        contentDescription = null,
                        modifier = Modifier.clip(CircleShape)
                    )
                    Column {
                        Text(text = "Hi Dani,")
                        Text(text = "What are you cooking today?")
                    }

                    IconButton(onClick = {
                        viewModel.navigateTo(HomeNavigator.NavigateToSearchScreen)
                    }) {
                        Icon(
                            imageVector = SearchDestination.icon,
                            contentDescription = SearchDestination.route
                        )
                    }
                }
            }
            item {
                HomeCategory()
            }
            item {
                Column {
                    Text(
                        text = "Featured Recipes",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Discover recipes with their nutrition",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            item {
                if (loadingIndicator) CircularProgressIndicator()
            }
            items(recipeState.recipeList ?: listOf()) { recipe: RecipePreview ->
                HomeList(recipe, modifier = Modifier.clickable {
                    viewModel.navigateTo(HomeNavigator.NavigateToDetailScreen(recipe.id?:""))
                })
            }
        }
    }
}