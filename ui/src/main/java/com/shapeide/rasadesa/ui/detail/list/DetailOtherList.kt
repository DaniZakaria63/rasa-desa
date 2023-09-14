package com.shapeide.rasadesa.ui.detail.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.presenter.detail.navigator.DetailNavigator
import com.shapeide.rasadesa.presenter.detail.viewmodel.DetailViewModel
import com.shapeide.rasadesa.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailOtherList(
    recipe: RecipePreview,
    onClicked: (String) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            onClicked(recipe.id ?: "err#4")
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.small
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = Dimens.normal)
            .padding(horizontal = Dimens.large)
    ) {
        Row {
            SubcomposeAsyncImage(
                model = recipe.image,
                contentDescription = null,
                loading = {
                    CircularProgressIndicator(modifier = Modifier.padding(Dimens.medium))
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .width(Dimens.iconLarge)
                    .height(Dimens.iconLarge)
            )

            Column(modifier = Modifier.padding(vertical = Dimens.normal)) {
                Text(
                    text = recipe.label.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.medium,
                        )
                        .fillMaxHeight()
                )
                FlowRow(Modifier.padding(start = Dimens.medium)) {
                    Text(
                        text = "${recipe.calories?.toInt()} cal",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(
                            end = Dimens.small,
                            top = Dimens.small,
                            bottom = Dimens.small
                        )
                    )

                    Text(
                        text = "${recipe.totalTime} min",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(
                            start = Dimens.small,
                            top = Dimens.small,
                            bottom = Dimens.small
                        )
                    )
                }
            }
        }
    }
}