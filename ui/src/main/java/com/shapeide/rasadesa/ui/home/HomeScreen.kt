package com.shapeide.rasadesa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.base.StateError
import com.shapeide.rasadesa.presenter.base.Status
import com.shapeide.rasadesa.presenter.home.state.HomeScreenState
import com.shapeide.rasadesa.presenter.main.viewmodel.MainViewModel
import com.shapeide.rasadesa.ui.R
import com.shapeide.rasadesa.ui.home.categories.HomeCategory
import com.shapeide.rasadesa.ui.home.list.HomeList
import timber.log.Timber


@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    var loadingIndicator by remember { mutableStateOf(true) }
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle(
        HomeScreenState(Unit)
    )

    val dummyList by viewModel.recipeDummy.collectAsStateWithLifecycle()
    LaunchedEffect(dummyList) {
        when (dummyList.status) {
            Status.ERROR -> {
                loadingIndicator = false
            }
            Status.DATA -> {
                loadingIndicator = false
                Timber.d("Everything is setting up")
            }
            Status.LOADING -> {
                loadingIndicator = true
            }
        }
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_outline_favorite),
                        contentDescription = null
                    )
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
                if(loadingIndicator) CircularProgressIndicator()
            }
            items(dummyList.recipeList ?: listOf()) { recipe: RecipePreview ->
                HomeList(recipe)
            }
        }
    }
}