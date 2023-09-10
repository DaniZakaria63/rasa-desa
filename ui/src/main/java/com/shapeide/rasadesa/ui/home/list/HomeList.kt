package com.shapeide.rasadesa.ui.home.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.domain.domain.RecipePreview
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeList(
    recipe: RecipePreview,
    onClicked: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClicked(recipe.id) }
    ) {
        Column(modifier = modifier) {
            SubcomposeAsyncImage(
                model = recipe.image,
                contentDescription = null,
                loading = {
                    CircularProgressIndicator()
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(25))
            )
            Text(text = recipe.label.toString())

        }
    }
}