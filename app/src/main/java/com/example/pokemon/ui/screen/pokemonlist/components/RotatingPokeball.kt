package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.R
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.theme.colorPrimary

@Composable
fun RotatingPokeball(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing)
        )
    )

    Image(
        modifier = modifier.graphicsLayer { rotationZ = angle },
        painter = painterResource(R.drawable.pokeball),
        colorFilter = ColorFilter.tint(colorPrimary),
        contentDescription = "Pokeball",
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonTheme {
        RotatingPokeball()
    }
}