package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.R
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.utils.PokemonImageUtil
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PokemonImage(pokemonId: Int) {
    GlideImage(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        previewPlaceholder = R.drawable.placeholder, // This is bugged
        failure = { UnknownPokemonImage() },
        imageModel = PokemonImageUtil.getImageUrlOf(pokemonId),
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.surface,
            highlightColor = Color.LightGray,
            durationMillis = 400,
            dropOff = 0.8f,
            tilt = 20f
        )
    )
}

@Preview
@Composable
fun PokemonImagePreview() {
    PokemonTheme {
        PokemonImage(pokemonId = 1)
    }
}
