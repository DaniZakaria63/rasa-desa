package com.shapeide.rasadesa.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shapeide.rasadesa.presenter.domain.AboutContentHolder
import com.shapeide.rasadesa.ui.R
import com.shapeide.rasadesa.ui.theme.Dimens

val baseContentList = listOf<AboutContentHolder>(
    AboutContentHolder(
        "Developer",
        "Dani Zakaria | Connect with me on LinkedIn",
        AboutContentHolder.AboutType.Link,
        "www.linkedin.com/in/dani-zakaria-168845194"
    ),
    AboutContentHolder(
        "Github",
        "Recipes application with nutrition details that build under Multi-Module of Clean Architecture with Compose, Hilt, Retrofit, Room, MVVM, et cetera.",
        AboutContentHolder.AboutType.Link,
        "https://github.com/DaniZakaria63/rasa-desa"
    ),
    AboutContentHolder(
        "License",
        "This mobile application (Rasa Desa) is provided by Dani Zakaria for personal use. By downloading and using the App, you agree to the following terms: " +
                "The image assets used within the App are subject to their own specific license terms, which can be found in the https://www.flaticon.com/free-icon/food-tray_4994337. " +
                "You may use the App for personal, non-commercial purposes only. Modification, reverse engineering, or distribution of the App is prohibited. " +
                "The App is provided as is, and Author disclaims any warranties. Dani is not liable for any damages. ",
        AboutContentHolder.AboutType.Text
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navigatorCallback: () -> Unit,
) {
    val contentList by remember {
        mutableStateOf(baseContentList)
    }
    val context = LocalContext.current

    LazyColumn {
        item {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.Text.bodyMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigatorCallback,
                        modifier = Modifier.padding(start = Dimens.small)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "icon.back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
            )
        }
        item {
            Divider(
                color = MaterialTheme.colorScheme.onSurface,
                thickness = 0.5.dp,
                modifier = Modifier.padding(bottom = Dimens.normal)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.large, bottom = Dimens.medium),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "icon.default",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(Dimens.iconXLarge)
                )
            }
        }
        item {
            Text(
                text = "Rasa Desa",
                style = MaterialTheme.typography.titleMedium,
                fontSize = Dimens.Text.display,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(CenterHorizontally)
            )
        }
        item {
            Text(
                text = "1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = Dimens.Text.display,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(CenterHorizontally)
                    .padding(bottom = Dimens.medium)
            )
        }
        items(contentList) {
            Box(modifier = Modifier.fillMaxWidth()) {

                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = Dimens.small
                    ), onClick = {
                        if (it.type == AboutContentHolder.AboutType.Link) openUrl(
                            context,
                            it.typeCallback.toString()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.large, vertical = Dimens.normal)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.medium, vertical = Dimens.normal)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = Dimens.Text.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = Dimens.normal)
                        )
                        Text(
                            text = it.value,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = Dimens.Text.body,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(bottom = Dimens.medium)
                        )
                    }
                }
                if (it.type == AboutContentHolder.AboutType.Link) {
                    Icon(
                        imageVector = Icons.Outlined.OpenInNew,
                        contentDescription = "icon.open",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(top = Dimens.large, end = Dimens.large)
                            .padding(end = Dimens.normal)
                            .size(Dimens.iconSmall)
                            .align(TopEnd)
                    )
                }
            }
        }
    }
}

fun openUrl(context: Context, url: String) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    context.startActivity(urlIntent)
}