package com.example.pokemon.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.alibaba.fastjson2.JSON
import com.commandiron.compose_loading.Wave
import com.example.pokemon.presentation.api.getPokemonById
import com.example.pokemon.presentation.model.Pokemon
import com.example.pokemon.presentation.model.myLoveList
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import kotlin.random.Random


@Composable
fun HomeComponent(modifier: Modifier = Modifier) {
    var id by remember { mutableStateOf(Random.Default.nextInt(1, 401)) }

    var pokemonObj: Pokemon? by remember {
        mutableStateOf(null)
    };

    var pokemonName by remember { mutableStateOf("") }
    var pokemonCover by remember { mutableStateOf("") }

    var isOk by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(id) {
        isOk = false;
        val pokemon = getPokemonById(id.toString())
        isOk = true;


        pokemonName = pokemon.title;
        pokemonCover = pokemon.coverUrl;
        pokemonObj = pokemon;
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Column {
                if (isOk) {
                    SubcomposeAsyncImage(
                        model = pokemonCover,
                        contentDescription = null, loading = {
                            Wave(modifier = Modifier.size(96.dp));
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = pokemonName,
                        modifier = Modifier
                            .align(CenterHorizontally)
                    )
                } else {
                    Wave(Modifier.size(116.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Footer(index = id, pokemon = pokemonObj, isOk = isOk) { newId ->
            id = newId;
        }
    }

}


@Composable
fun Footer(index: Int, pokemon: Pokemon?, isOk: Boolean, onUpdateIndex: (Int) -> Unit) {

    var isLoved by remember {
        mutableStateOf(false)
    };

    LaunchedEffect(pokemon) {
        isLoved = false;
        myLoveList.forEach { item ->
            {
                if (item.id == pokemon?.id) {
                    isLoved = true;
                }
            }
        }
    }

    Box {
        Row(modifier = Modifier.align(Alignment.Center)) {
            Button(
                enabled = isOk,
                modifier = Modifier
                    .size(ButtonDefaults.DefaultButtonSize)
                    .background(Color.Transparent),

                onClick = {
                    onUpdateIndex(if (index - 1 == 0) 1 else index - 1);
                }
            ) {
                Icon(

                    modifier = Modifier.size(ButtonDefaults.LargeIconSize),
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = "上一張",
                    tint = Color.White
                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                enabled = isOk,
                modifier = Modifier.size(ButtonDefaults.DefaultButtonSize),
                onClick = {
                    if (pokemon == null) return@Button;

                    if (isLoved == false) {
                        myLoveList.add(pokemon)
                        isLoved = true;
                    } else if (isLoved) {
                        val result = myLoveList.find { item ->
                            item.id == pokemon?.id
                        };
                        if (result != null) {
                            myLoveList.remove(result);
                        }
                        isLoved = false;
                    }
                    Log.d("mylove ", JSON.toJSONString(myLoveList));
                }
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.LargeIconSize),
                    imageVector = Icons.Rounded.Favorite, contentDescription = null,
                    tint = if (isLoved) Color.White else Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                enabled = isOk,
                modifier = Modifier
                    .size(ButtonDefaults.DefaultButtonSize)
                    .background(Color.Transparent),

                onClick = {
                    onUpdateIndex(index + 1)
                }
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.LargeIconSize),
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = "下一張",
                    tint = Color.White
                )

            }
        }
    }
}