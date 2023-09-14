package com.shapeide.rasadesa.ui.detail.nutrient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
    nutrients: Nutrients?,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val nutrientValueList = remember {
        mutableListOf<NutrientsSub>()
    }

    LaunchedEffect(nutrientValueList) {
        Timber.i("Nutrient value list effect CALLED")
        nutrientValueList.clear()
        val values = viewModel.extractValueList(nutrients ?: Nutrients())
        nutrientValueList.addAll(values)
    }

    Column(Modifier.fillMaxWidth()) {
        nutrientValueList.forEach {  value ->
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(value.label)
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)){
                        append(" :  ${value.quantity?.toInt()} ${value.unit}")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = Dimens.large, vertical = Dimens.normal)
            )
        }
    }
}