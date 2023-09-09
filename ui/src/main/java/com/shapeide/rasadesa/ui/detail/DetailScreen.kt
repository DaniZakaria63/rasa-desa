package com.shapeide.rasadesa.ui.detail

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun DetailScreen(
    recipe_id: String? = ""
) {
    Surface {
        Text(text = "Just text from detail screen with id $recipe_id")
    }
}