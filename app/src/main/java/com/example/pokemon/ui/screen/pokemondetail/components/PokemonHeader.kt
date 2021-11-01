package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.example.pokemon.R
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.data.domainmodel.Type
import com.example.pokemon.ui.shared.UnknownPokemonImage
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.theme.gray_21
import com.example.pokemon.utils.PokemonImageUtil
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun PokemonHeader(pokemon: Pokemon?, disableImageFetching: Boolean = false) {
    var palette by remember { mutableStateOf<Palette?>(null) }
    val backgroundColor = remember { Animatable(gray_21) }
    LaunchedEffect(palette) {
        palette?.dominantSwatch?.rgb?.let {
            backgroundColor.animateTo(
                targetValue = Color(it),
                animationSpec = tween(
                    durationMillis = 400,
                    easing = LinearEasing
                )
            )
        }
    }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 4.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(0, 0, 25, 25)
                )
                .clip(RoundedCornerShape(0, 0, 25, 25))
                .background(color = backgroundColor.value)
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
                    loading = { UnknownPokemonImage(modifier = Modifier.align(Alignment.Center)) },
                    failure = { UnknownPokemonImage(modifier = Modifier.align(Alignment.Center)) },
                    contentScale = ContentScale.Fit,
                    previewPlaceholder = R.drawable.placeholder, // This is bugged
                    imageModel = PokemonImageUtil.getImageUrlOf(pokemon.id)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonHeaderPreview() {
    val pokemon = Pokemon(
        name = "Charmender",
        types = listOf(Type.DRAGON, Type.FIRE, Type.ELECTRIC, Type.STEEL)
    )
    PokemonTheme {
        PokemonHeader(pokemon, true)
    }
}