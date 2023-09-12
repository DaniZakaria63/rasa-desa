package com.shapeide.rasadesa.ui.home.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shapeide.rasadesa.ui.theme.Dimens

@Composable
fun HomeCategory(
    modifier: Modifier = Modifier,
    list: Array<HomeCategoryMenu> = HomeCategoryMenu.values()
) {
    Row(modifier = modifier) {
        list.forEach { category ->
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimens.medium)
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.title,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimens.iconNormal)
                        .clip(RoundedCornerShape(25))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = Dimens.medium),
                )
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = Dimens.normal)
                )
            }
        }
    }
}
