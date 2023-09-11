package com.shapeide.rasadesa.ui.detail.nutrient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.domain.domain.NutrientsSub
import com.shapeide.rasadesa.presenter.detail.viewmodel.DetailViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun NutrientSection(
    nutrients: Nutrients?,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val nutrientValueList = rememberSaveable {
        mutableListOf<NutrientsSub>()
    }

    LaunchedEffect("restart_list") {
        nutrientValueList.clear()
        val values = viewModel.extractValueList(nutrients ?: Nutrients())
        nutrientValueList.addAll(values)
    }

    Column {
        nutrientValueList.forEach {  value ->
            Text(text = "Nutrient Info : ${value.label} ${value.quantity} ${value.unit}")
        }
    }
}