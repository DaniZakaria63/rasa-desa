package com.shapeide.rasadesa.ui.home.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeCategory(
    list: Array<HomeCategoryMenu> = HomeCategoryMenu.values()
) {
    Column {
        Text(
            text = "Category",
            style = MaterialTheme.typography.titleMedium
        )
        LazyRow {
            items(list) { category ->
                Column {
                    Icon(
                        painter = painterResource(id = category.icon),
                        contentDescription = category.title,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(text = category.title)
                }
            }
        }
    }
}
