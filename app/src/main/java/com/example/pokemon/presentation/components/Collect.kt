package com.example.pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.commandiron.compose_loading.Wave

@Composable
fun CollectComponent(iconUrl: String, text: String, modifier: Modifier = Modifier) {

    Chip(onClick = {}, modifier = modifier,
        label = {
            Text(text = text)
        },
        icon = {
            SubcomposeAsyncImage(
                model = iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(ChipDefaults.LargeIconSize)
                    .wrapContentSize(align = Alignment.Center),
                loading = {
                    Wave(modifier=Modifier.size(96.dp));
                }

            )
        }
    )

}