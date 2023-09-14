package com.shapeide.rasadesa.ui.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HourglassBottom
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.presenter.detail.navigator.DetailNavigator
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState
import com.shapeide.rasadesa.presenter.detail.state.DetailTabState
import com.shapeide.rasadesa.presenter.detail.viewmodel.DetailViewModel
import com.shapeide.rasadesa.presenter.domain.DetailTab
import com.shapeide.rasadesa.ui.detail.ingredient.IngredientSection
import com.shapeide.rasadesa.ui.detail.list.DetailOtherList
import com.shapeide.rasadesa.ui.detail.more.MoreSection
import com.shapeide.rasadesa.ui.detail.nutrient.NutrientSection
import com.shapeide.rasadesa.ui.theme.Dimens
import com.shapeide.rasadesa.ui.widget.CustomTabMenu
import com.shapeide.rasadesa.ui.widget.shimmerBrush
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun DetailScreen(
    recipe_id: String? = "",
    viewModel: DetailViewModel = hiltViewModel(),
    navigatorCallback: (DetailNavigator) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listScrollState = rememberLazyListState()
    var loadingProcess by remember {
        mutableStateOf(true)
    }
    val navigatorState: DetailNavigator? by viewModel.navigatorDetail.collectAsStateWithLifecycle(
        initialValue = null
    )
    LaunchedEffect(navigatorState) {
        Timber.i("Navigator Information: $navigatorState")
        navigatorState?.let { state -> navigatorCallback(state) }
    }

    val detailScreenState: DetailScreenState by viewModel.detailScreenState.collectAsStateWithLifecycle(
        initialValue = DetailScreenState()
    )
    val detailTabState: DetailTabState by viewModel.tabState.collectAsStateWithLifecycle(
        initialValue = DetailTabState()
    )
    LaunchedEffect(recipe_id) {
        listScrollState.animateScrollToItem(index = 0)
        viewModel.getDetail(recipe_id)
    }

    var tabIndex by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(detailTabState) {
        tabIndex = detailTabState.tabActiveState?.ordinal ?: 0
    }

    LazyColumn(state = listScrollState) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                SubcomposeAsyncImage(
                    model = detailScreenState.header.image,
                    contentDescription = "detail.image",
                    contentScale = ContentScale.Crop,
                    onLoading = { loadingProcess = true },
                    onSuccess = { loadingProcess = false },
                    onError = { loadingProcess = false},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(shimmerBrush(showShimmer = loadingProcess))
                )

                IconButton(
                    onClick = {
                        coroutineScope.launch { viewModel.navigateTo(DetailNavigator.NavigateToHomeScreen) }
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = Dimens.large, start = Dimens.large)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "icon.back",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .size(Dimens.iconXLarge)
                            .clip(CircleShape)
                            .padding(Dimens.normal)
                    )
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch { viewModel.addToFavorite() }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = Dimens.large, end = Dimens.large)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "icon.favorite",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .size(Dimens.iconXLarge)
                            .clip(CircleShape)
                            .padding(Dimens.normal)
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = Dimens.small
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(top = 250.dp)
                        .padding(start = Dimens.normal, end = Dimens.large)
                        .padding(horizontal = Dimens.large, vertical = Dimens.medium)
                ) {
                    Text(
                        text = detailScreenState.header.source,
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = Dimens.Text.label,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .padding(
                                top = Dimens.medium,
                                start = Dimens.medium,
                                bottom = Dimens.small,
                            )
                            .clip(RoundedCornerShape(13))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = Dimens.large, vertical = Dimens.small)
                    )

                    Text(
                        text = detailScreenState.header.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = Dimens.Text.title,
                        modifier = Modifier.padding(
                            horizontal = Dimens.medium,
                            vertical = Dimens.medium,
                        )
                    )

                    Divider(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(Dimens.medium)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.normal, bottom = Dimens.medium),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = Dimens.medium),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.HourglassBottom,
                                contentDescription = "icon.time",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(Dimens.iconSmall)
                            )
                            Text(
                                text = "${detailScreenState.header.time} min",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Light,
                                fontSize = Dimens.Text.body,
                                modifier = Modifier.padding(Dimens.small)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(horizontal = Dimens.medium),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Fastfood,
                                contentDescription = "icon.time",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(Dimens.iconSmall),
                            )
                            Text(
                                text = "${detailScreenState.header.calories} cal",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Light,
                                fontSize = Dimens.Text.body,
                                modifier = Modifier.padding(Dimens.small)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(horizontal = Dimens.medium),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.MonitorWeight,
                                contentDescription = "icon.time",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(Dimens.iconSmall)
                            )
                            Text(
                                text = "${detailScreenState.header.weight} gr",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Light,
                                fontSize = Dimens.Text.body,
                                modifier = Modifier.padding(Dimens.small)
                            )
                        }
                    }
                }
            }
        }

        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomTabMenu(
                    tabList = detailTabState.tabList.map { it.name },
                    selectedItemIndex = tabIndex,
                    tabWidth = 120.dp,
                    modifier = Modifier
                        .padding(
                            top = Dimens.normal,
                            start = Dimens.medium,
                            end = Dimens.medium,
                            bottom = Dimens.normal
                        )
                ) {
                    coroutineScope.launch {
                        viewModel.updateTabPosition(detailTabState.checkTabByName(it))
                    }
                }
            }
        }

        item {
            when (detailTabState.tabActiveState) {
                DetailTab.INGREDIENTS -> IngredientSection(detailScreenState.ingredients)
                DetailTab.NUTRIENTS -> NutrientSection(detailScreenState.nutrients)
                DetailTab.MORE -> MoreSection(
                    detailScreenState.others,
                    detailScreenState.digest
                )

                else -> Text("You should not seen this")
            }
        }

        item {
            Text(
                text = "Other Recipes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.medium,
                        vertical = Dimens.large,
                    )
            )
        }

        items(detailScreenState.moreRecipes ?: listOf()) { recipe ->
            DetailOtherList(recipe) { recipeId ->
                coroutineScope.launch {
                    viewModel.navigateTo(
                        DetailNavigator.NavigateToAnotherDetailScreen(
                            recipeId
                        )
                    )
                }
            }
        }
    }
}
