package com.shapeide.rasadesa.ui.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState
import com.shapeide.rasadesa.presenter.detail.state.DetailTabState
import com.shapeide.rasadesa.presenter.detail.viewmodel.DetailViewModel
import com.shapeide.rasadesa.presenter.domain.DetailTab
import com.shapeide.rasadesa.ui.detail.ingredient.IngredientSection
import com.shapeide.rasadesa.ui.detail.list.DetailOtherList
import com.shapeide.rasadesa.ui.detail.more.MoreSection
import com.shapeide.rasadesa.ui.detail.nutrient.NutrientSection
import timber.log.Timber


@Composable
fun DetailScreen(
    recipe_id: String? = "",
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detailScreenState: DetailScreenState by viewModel.detailScreenState.collectAsStateWithLifecycle(
        initialValue = DetailScreenState()
    )
    val detailTabState: DetailTabState by viewModel.tabState.collectAsStateWithLifecycle(
        initialValue = DetailTabState()
    )
    LaunchedEffect("get_recipe") {
        Timber.i("NavHost Parsed ID: $recipe_id")
        viewModel.getDetail(recipe_id)
    }

    var tabIndex by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(detailTabState) {
        tabIndex = detailTabState.tabActiveState?.ordinal ?: 0
    }

    Surface {
        LazyColumn {
            item {
                // TODO: Header Image
                AsyncImage(
                    model = detailScreenState.header.image,
                    contentDescription = "detail.image"
                )
            }
            item {
                // TODO: Header Card Title
                Text(
                    text = "Calories ${detailScreenState.header.calories}"
                )
                Text(
                    text = "Time ${detailScreenState.header.time}"
                )
            }
            item {
                // TODO: Tab Section
                TabRow(selectedTabIndex = tabIndex) {
                    detailTabState.tabList.forEach { tab ->
                        Tab(
                            selected = tabIndex == tab.ordinal,
                            onClick = { viewModel.updateTabPosition(tab) },
                            text = {
                                Text(text = tab.name)
                            }
                        )
                    }
                }
                Text(text = "Tab Sections")
            }
            item {
                // TODO: Detail Selected Section
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
            items(detailScreenState.moreRecipes ?: listOf()) {recipe->
                // TODO: More relevant contents
                DetailOtherList(recipe)
            }
        }
    }
}