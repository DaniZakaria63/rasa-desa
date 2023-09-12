package com.shapeide.rasadesa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shapeide.rasadesa.ui.R

private val roboto = FontFamily(
    Font(R.font.roboto, FontWeight.Normal)
)

private val raleway = FontFamily(
    Font(R.font.raleway, FontWeight.Normal)
)

private val prata = FontFamily(
    Font(R.font.prata, FontWeight.Normal)
)

val customTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = prata,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Light,
    ),
    bodyLarge = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Medium,
    ),
    bodyMedium = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Normal,
    ),
    labelMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
    )
)