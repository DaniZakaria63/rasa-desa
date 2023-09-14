package com.shapeide.rasadesa.ui.home.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.ui.theme.Dimens
import com.shapeide.rasadesa.ui.widget.shimmerBrush

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeList(
    recipe: RecipePreview,
    modifier: Modifier = Modifier,
    onClicked: (String?) -> Unit,
) {
    var loadingProcess by remember {
        mutableStateOf(true)
    }

    Card(
        onClick = { onClicked(recipe.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.small
        ),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(vertical = Dimens.medium)
    ) {
        Column(modifier = modifier) {
            SubcomposeAsyncImage(
                model = recipe.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { loadingProcess = true },
                onSuccess = { loadingProcess = false },
                onError = { loadingProcess = false},
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.normal))
                    .background(shimmerBrush(showShimmer = loadingProcess))
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = recipe.label.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontSize = Dimens.Text.subtitle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(Dimens.normal)
            )

            /*TODO: Restructure this into data list and do looping*/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.normal)
            ) {
                Column(modifier = Modifier.padding(horizontal = Dimens.small)) {
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = Dimens.Text.body,
                    )

                    Text(
                        text = recipe.mealType.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = Dimens.Text.body,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = Dimens.medium)
                ) {
                    Text(
                        text = "Calories",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = Dimens.Text.body,
                    )
                    Text(
                        text = recipe.caloriesInt.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = Dimens.Text.body,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = Dimens.normal)
                ) {
                    Text(
                        text = "Time",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = Dimens.Text.body,
                    )
                    Text(
                        text = "${recipe.totalTime} min",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = Dimens.Text.body,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            FlowRow(
                modifier = Modifier
                    .padding(start = Dimens.normal, end = Dimens.normal, top = Dimens.medium)
            ) {
                recipe.dietLabels.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(Dimens.small)
                            .padding(horizontal = Dimens.normal)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = Dimens.small))
                }
            }
            Spacer(modifier = Modifier.padding(Dimens.medium))
        }
    }
}