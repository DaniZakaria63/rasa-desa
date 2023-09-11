package com.shapeide.rasadesa.ui.detail.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.shapeide.rasadesa.domain.domain.Digest
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoreSection(
    others: DetailScreenState.Others,
    digests: List<Digest>?
) {
    Column {
        Text(text = "Meal Type")
        FlowRow {
            others.mealType?.forEach { meal ->
                Text(text = meal)
            }
        }
        digests?.forEach { digest ->
            Text(text = "Label: ${digest.label} ${digest.total} ${digest.unit}")
        }
    }
}