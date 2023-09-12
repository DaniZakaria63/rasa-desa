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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.domain.domain.RecipePreview
import com.shapeide.rasadesa.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeList(
    recipe: RecipePreview,
    onClicked: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClicked(recipe.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.small
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = Dimens.normal)
    ) {
        Column(modifier = modifier) {
            SubcomposeAsyncImage(
                model = recipe.image,
                contentDescription = null,
                loading = {
                    CircularProgressIndicator()
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.normal))
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Text(
                text = recipe.label.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = Dimens.normal, vertical = Dimens.medium)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.normal)
            ) {
                Text(
                    text = "Type",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(0.5f)
                )
                Text(
                    text = recipe.mealType.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = Dimens.small)
                        .weight(0.5f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.normal)
            ) {
                Text(
                    text = "Calories",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(0.5f)
                )
                Text(
                    text = recipe.caloriesInt.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = Dimens.small)
                        .weight(0.5f)
                )
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