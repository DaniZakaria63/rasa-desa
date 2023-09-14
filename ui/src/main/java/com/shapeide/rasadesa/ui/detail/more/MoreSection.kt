package com.shapeide.rasadesa.ui.detail.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.shapeide.rasadesa.domain.domain.Digest
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState
import com.shapeide.rasadesa.ui.theme.Dimens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoreSection(
    others: DetailScreenState.Others,
    digests: List<Digest>?
) {
    Column(modifier = Modifier.padding(horizontal = Dimens.large)) {
        Text(
            text = "Health Labels",
            fontSize = Dimens.Text.bodyMedium,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = Dimens.normal)
        )
        FlowRow(modifier = Modifier.padding(vertical = Dimens.normal)) {
            others.healthLabels?.forEach { meal ->
                Text(
                    text = meal,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = Dimens.Text.body,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .padding(
                            top = Dimens.normal,
                            bottom = Dimens.small,
                        )
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = Dimens.normal, vertical = Dimens.small)
                )
            }
        }
        Text(
            text = "Digest",
            fontSize = Dimens.Text.bodyMedium,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = Dimens.large, bottom = Dimens.normal)
        )
        digests?.forEach { digest ->
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(digest.label)
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(" :  ${digest.total?.toInt()} ${digest.unit}")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = Dimens.normal, horizontal = Dimens.normal)
            )
        }
    }
}