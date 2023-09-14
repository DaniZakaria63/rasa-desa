package com.shapeide.rasadesa.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.presenter.search.navigator.SearchNavigator
import com.shapeide.rasadesa.presenter.search.viewmodel.SearchViewModel
import com.shapeide.rasadesa.ui.theme.Dimens
import com.shapeide.rasadesa.ui.widget.CustomTabMenu
import com.shapeide.rasadesa.ui.widget.shimmerBrush
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    query: String?,
    viewModel: SearchViewModel = hiltViewModel(),
    navigatorCallback: (SearchNavigator) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val horizontalScrollState = rememberScrollState()
    val cardConfigurationWidth = remember {
        (configuration.screenWidthDp / 2).dp
    }
    val searchListParams = rememberSaveable {
        viewModel._queryListParams
    }
    var tabIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(query) {
        viewModel.parseQuery(query)
    }

    val navigatorState by viewModel.navigation.collectAsStateWithLifecycle(initialValue = null)
    LaunchedEffect(navigatorState) {
        navigatorState?.let { navigatorCallback(it) }
    }

    val searchQueryState by viewModel.queryState.collectAsStateWithLifecycle()

    LaunchedEffect(searchQueryState) {
        tabIndex = searchQueryState.ordinal
    }

    val searchDataSearch by viewModel.searchDataState.collectAsStateWithLifecycle()

    val searchErrorState by viewModel.searchErrorState.collectAsStateWithLifecycle(
        initialValue = null
    )

    Column {
        LazyColumn {
            item {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = searchQueryState.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = Dimens.Text.bodyMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.navigateTo(
                                        SearchNavigator.NavigateToHomeScreen
                                    )
                                }
                            },
                            modifier = Modifier.padding(start = Dimens.small)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "icon.back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                )
            }
            item {
                Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onSurface)
            }
            item {
                Row(
                    modifier = Modifier.horizontalScroll(horizontalScrollState),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(Dimens.normal)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FilterAlt,
                            contentDescription = "icon.filter",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "Filter",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = Dimens.Text.body
                        )
                    }
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp)
                    )
                    CustomTabMenu(
                        tabList = searchListParams.map { it.name },
                        selectedItemIndex = tabIndex,
                        modifier = Modifier
                            .padding(
                                top = Dimens.normal,
                                start = Dimens.medium,
                                end = Dimens.medium,
                                bottom = Dimens.normal
                            )
                    ) { tab ->
                        coroutineScope.launch {
                            viewModel.parseQuery(tab)
                        }
                    }
                }
            }
            item {
                Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onSurface)
            }
        }
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = cardConfigurationWidth)) {
            items(searchDataSearch.recipeList ?: listOf()) { recipe ->
                var loadingProcess by remember {
                    mutableStateOf(true)
                }
                Card(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.navigateTo(
                                SearchNavigator.NavigateToDetailScreen(
                                    id = recipe.id ?: "err#5"
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .width(cardConfigurationWidth)
                        .padding(horizontal = Dimens.normal, vertical = Dimens.normal)
                ) {
                    Column {
                        SubcomposeAsyncImage(
                            model = recipe.image,
                            contentDescription = "recipe.image",
                            contentScale = ContentScale.Crop,
                            onLoading = { loadingProcess = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(cardConfigurationWidth)
                                .background(shimmerBrush(showShimmer = loadingProcess))
                        )
                        Text(
                            text = recipe.labelShorter,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = Dimens.Text.body,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = Dimens.normal,
                                vertical = Dimens.medium
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                top = Dimens.small,
                                bottom = Dimens.normal,
                                start = Dimens.normal
                            )
                        ) {
                            Text(
                                text = "${recipe.caloriesInt.toString()} Cal",
                                modifier = Modifier.padding(horizontal = Dimens.normal),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "${recipe.totalTime.toString()} Min",
                                modifier = Modifier.padding(horizontal = Dimens.normal),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}