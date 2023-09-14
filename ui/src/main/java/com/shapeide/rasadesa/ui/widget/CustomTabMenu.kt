package com.shapeide.rasadesa.ui.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shapeide.rasadesa.presenter.domain.DetailTab
import com.shapeide.rasadesa.ui.theme.Dimens


@Composable
fun CustomTabMenu(
    tabList: List<String>,
    selectedItemIndex: Int,
    tabWidth: Dp = 100.dp,
    modifier: Modifier,
    onClick: (String) -> Unit
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
        label = "indicator.offset"
    )

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .height(intrinsicSize = IntrinsicSize.Min)
            .then(modifier)
    ) {
        TabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = MaterialTheme.colorScheme.primary
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape)
        )
        {
            tabList.forEachIndexed { index, tab ->
                val isSelected = index == selectedItemIndex
                TabItem(
                    isSelected = isSelected,
                    text = tab,
                    tabWidth = tabWidth,
                    onClick = { onClick(tab) }
                )
            }
        }
    }
}

@Composable
fun TabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(width = indicatorWidth)
            .offset(x = indicatorOffset)
            .clip(shape = CircleShape)
            .background(color = indicatorColor)
    )
}

@Composable
fun TabItem(
    isSelected: Boolean,
    text: String,
    tabWidth: Dp,
    onClick: () -> Unit,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(easing = LinearEasing),
        label = "tab.text.color"
    )

    Text(text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
        fontSize = Dimens.Text.body,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .width(tabWidth)
            .padding(
                vertical = Dimens.small,
                horizontal = Dimens.normal
            )
    )
}