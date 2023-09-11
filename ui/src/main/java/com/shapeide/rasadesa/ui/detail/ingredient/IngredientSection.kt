package com.shapeide.rasadesa.ui.detail.ingredient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.shapeide.rasadesa.domain.domain.Ingredients
import com.shapeide.rasadesa.ui.R

@Composable
fun IngredientSection(ingredients: List<Ingredients>?) {
    Column {
        ingredients?.forEach{ ingredient ->
            var expanded by rememberSaveable {
                mutableStateOf(false)
            }

            Row {
                AsyncImage(
                    model = ingredient.image,
                    contentDescription = "ingredient.image"
                )
                Text(text = ingredient.text ?: "-")

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) {
                            stringResource(id = R.string.expand_less)
                        } else {
                            stringResource(id = R.string.expand_more)
                        }
                    )
                }
            }
            if (expanded) {
                Row {
                    Column {
                        Text(text = "Quantity: ${ingredient.quantity} ${ingredient.measure}")
                        Text(text = "Weight: ${ingredient.weight}")
                    }
                    Column {
                        Text(text = "Category: ${ingredient.foodCategory}")
                        Text(text = "Food: ${ingredient.food ?: "-"}")
                    }
                }
            }
        }
    }
}