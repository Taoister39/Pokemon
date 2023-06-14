/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.pokemon.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.pokemon.presentation.components.CollectComponent
import com.example.pokemon.presentation.components.HomeComponent
import com.example.pokemon.presentation.model.myLoveList
import com.example.pokemon.presentation.theme.PokemonTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
@Preview
fun WearApp() {

    val maxPage = 2;
//    val pageList = listOf(HomeScreen(), CollectScreen())

    var selectedPage by remember {
        mutableStateOf(0)
    };

    var finalValue by remember { mutableStateOf(0) }
    val animatedSelectedPage by animateFloatAsState(
        targetValue = selectedPage.toFloat(),
    ) {
        finalValue = it.toInt()
    }


    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = animatedSelectedPage - finalValue
            override val selectedPage: Int
                get() = finalValue
            override val pageCount: Int
                get() = maxPage
        }
    }

    val listState = rememberScalingLazyListState();

    val contentModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)



    PokemonTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Scaffold(

            positionIndicator = {
                PositionIndicator(scalingLazyListState = listState)
            },
//            pageIndicator = {
//                HorizontalPageIndicator(pageIndicatorState = pageIndicatorState)
//            },
        ) {

            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                autoCentering = AutoCenteringParams(itemIndex = 0),
                state = listState
            ) {
                item { HomeComponent(contentModifier) }


                items(myLoveList) {pokemon ->
                     CollectComponent(
                        iconUrl = pokemon.coverUrl,
                        text = pokemon.title,
                        modifier = contentModifier
                    )
                }
            }
//            HorizontalPager(pageCount = maxPage) { page ->
//                if(page == 0) HomeScreen()
//                else if(page == 1) CollectScreen()
//            }


        }
    }
}

