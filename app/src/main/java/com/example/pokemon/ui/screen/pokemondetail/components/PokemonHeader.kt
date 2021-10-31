package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.example.pokemon.R
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.shared.UnknownPokemonImage
import com.example.pokemon.ui.theme.gray_21
import com.example.pokemon.utils.PokemonImageUtil
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun PokemonHeader(pokemon: Pokemon?, disableImageFetching: Boolean = false) {
    var palette by remember { mutableStateOf<Palette?>(null) }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(0, 0, 25, 25))
                .background(color = palette?.dominantSwatch?.rgb?.let { Color(it) } ?: gray_21)
        ) {
            if (pokemon == null || disableImageFetching) {
                UnknownPokemonImage(modifier = Modifier.align(Alignment.Center))
            } else {
                GlideImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                        .aspectRatio(1f),
                    bitmapPalette = BitmapPalette {
                        palette = it
                    },
                    failure = { UnknownPokemonImage(modifier = Modifier.align(Alignment.Center)) },
                    contentScale = ContentScale.Fit,
                    previewPlaceholder = R.drawable.placeholder, // This is bugged
                    imageModel = PokemonImageUtil.getImageUrlOf(pokemon.id),
                    shimmerParams = ShimmerParams(
                        baseColor = Color.Transparent,
                        highlightColor = Color.LightGray,
                        durationMillis = 400,
                        dropOff = 0.8f,
                        tilt = 20f
                    )
                )
            }
        }
    }
}