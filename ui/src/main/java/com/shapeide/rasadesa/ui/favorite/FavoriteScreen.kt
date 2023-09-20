package com.shapeide.rasadesa.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.presenter.favorite.navigator.FavoriteNavigator
import com.shapeide.rasadesa.presenter.favorite.viewmodel.FavoriteViewModel
import com.shapeide.rasadesa.ui.theme.Dimens
import com.shapeide.rasadesa.ui.widget.shimmerBrush
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigatorCallback: (FavoriteNavigator) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var loadingProcess by remember {
        mutableStateOf(true)
    }
    val favoriteDataState by viewModel.favoriteDataState.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    var errorState by remember {
        mutableStateOf(Throwable())
    }
    LaunchedEffect(Unit) {
        viewModel.getRecipes()
        viewModel.navigator.collectLatest {
            Timber.i("NOW I BEEN CALLED")
            navigatorCallback(it)
        }
        viewModel.errorState.collectLatest {
            Timber.e(it)
        }
    }

    LazyColumn {
        item {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.Text.bodyMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.navigateTo(
                                    FavoriteNavigator.NavigateToHomeScreen
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
            Divider(
                color = MaterialTheme.colorScheme.onSurface,
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = Dimens.normal)
            )
            if (loadingState) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.medium),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.iconXLarge, Dimens.iconXLarge)
                )
            }
        }
        items(favoriteDataState) { recipe ->
            Card(
                onClick = {
                    coroutineScope.launch {
                        viewModel.navigateTo(
                            FavoriteNavigator.NavigateToDetailScreen(
                                detailId = recipe.id ?: "err#5"
                            )
                        )
                    }
                }, elevation = CardDefaults.cardElevation(
                    defaultElevation = Dimens.small
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.normal, vertical = Dimens.normal)
            ) {
                Row {
                    SubcomposeAsyncImage(
                        model = recipe.image,
                        contentDescription = "recipe.image",
                        contentScale = ContentScale.Crop,
                        onLoading = { loadingProcess = true },
                        onSuccess = { loadingProcess = false },
                        onError = { loadingProcess = false },
                        modifier = Modifier
                            .height(Dimens.iconXLarge)
                            .width(Dimens.iconXLarge)
                            .background(shimmerBrush(showShimmer = loadingProcess))
                    )
                    Text(
                        text = recipe.label.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = Dimens.Text.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.normal,
                                vertical = Dimens.medium
                            )
                            .weight(1f)
                    )

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.updateFavoriteRecipes(
                                    recipe
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(Dimens.small)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "icon.favorite",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .size(Dimens.iconMedium)
                                .clip(CircleShape)
                                .padding(Dimens.small)
                        )
                    }
                }
            }
        }
    }
}