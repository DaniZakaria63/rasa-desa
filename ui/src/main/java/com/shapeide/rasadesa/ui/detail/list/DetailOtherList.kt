package com.shapeide.rasadesa.ui.detail.list

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.detail.navigator.DetailNavigator
import com.shapeide.rasadesa.presenter.detail.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailOtherList(
    recipe: RecipePreview,
    viewModel: DetailViewModel = hiltViewModel()
) {
    Card(onClick = {
        viewModel.navigateTo(
            DetailNavigator.NavigateToAnotherDetailScreen(
                recipe.id ?: "err#4"
            )
        )
    }) {
        Text("Name: ${recipe.label}")
    }
}