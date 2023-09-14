package com.shapeide.rasadesa.ui.detail.ingredient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.shapeide.rasadesa.domain.domain.Ingredients
import com.shapeide.rasadesa.ui.R
import com.shapeide.rasadesa.ui.theme.Dimens

@Composable
fun IngredientSection(ingredients: List<Ingredients>?) {
    Column {
        ingredients?.forEach { ingredient ->
            var expanded by rememberSaveable {
                mutableStateOf(false)
            }

            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = Dimens.small
                ),
                modifier = Modifier
                    .padding(horizontal = Dimens.large, vertical = Dimens.normal)
                    .fillMaxWidth()
            ) {

                Column {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        SubcomposeAsyncImage(
                            model = ingredient.image,
                            contentDescription = "detail.ingredient.image",
                            contentScale = ContentScale.Crop,
                            loading = {
                                CircularProgressIndicator()
                            },
                            modifier = Modifier
                                .width(Dimens.iconLarge)
                                .height(Dimens.iconLarge)
                                .clip(RoundedCornerShape(14))
                        )

                        Text(
                            text = ingredient.text ?: "-",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = Dimens.normal, vertical = Dimens.medium)
                        )

                        IconButton(
                            onClick = { expanded = !expanded },
                            modifier = Modifier
                                .clip(RoundedCornerShape(13))
                                .background(MaterialTheme.colorScheme.tertiaryContainer)
                                .padding(Dimens.xsmall)
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                contentDescription = if (expanded) {
                                    stringResource(id = R.string.expand_less)
                                } else {
                                    stringResource(id = R.string.expand_more)
                                },
                                modifier = Modifier
                                    .width(Dimens.iconSmall)
                                    .height(Dimens.iconSmall)
                            )
                        }
                    }

                    if (expanded) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    vertical = Dimens.medium,
                                    horizontal = Dimens.normal
                                )
                                .padding(bottom = Dimens.normal)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(end = Dimens.normal)
                                    .weight(0.5f)
                            ) {
                                Text(
                                    text = "Quantity: ${ingredient.quantity} ${ingredient.measure}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Weight: ${ingredient.weight}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontSize = 18.sp
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(start = Dimens.normal)
                                    .weight(0.5f)
                            ) {
                                Text(
                                    text = "Food: ${ingredient.food}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Category: ${ingredient.foodCategory ?: "-"}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}