package com.shapeide.rasadesa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable


@Composable
fun RasaDesaTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = customTypography,
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        CustomLightColors
    } else {
        CustomDarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = typography
    )
}