package com.shapeide.rasadesa.ui.detail.nutrient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.domain.domain.NutrientsSub
import com.shapeide.rasadesa.presenter.detail.viewmodel.DetailViewModel
import com.shapeide.rasadesa.ui.theme.Dimens
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun NutrientSection(
    nutrients: List<NutrientsSub>?
) {

    Column(Modifier.fillMaxWidth()) {
        nutrients?.forEach { value ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = "icon.circle",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(
                            start = Dimens.large,
                            end = Dimens.small,
                            top = Dimens.normal,
                            bottom = Dimens.normal
                        )
                        .size(Dimens.iconSmall)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(value.label)
                        }

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(" :  ${value.quantity?.toInt()} ${value.unit}")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(end = Dimens.large, top = Dimens.normal, bottom = Dimens.normal)
                )
            }
        }
    }
}