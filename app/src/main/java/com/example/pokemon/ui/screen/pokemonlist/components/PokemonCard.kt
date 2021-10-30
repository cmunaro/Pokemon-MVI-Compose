package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.R
import com.example.pokemon.ui.theme.PokemonTheme
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

private const val POKEMON_IMAGE_BASE_URL =
    "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/"

@Composable
fun PokemonCard(pokemonName: String, pokemonId: Int, onClick: (pokemonId: Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(pokemonId) }
    ) {
        Column {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f),
                failure = { UnknownImage() },
                imageModel = "$POKEMON_IMAGE_BASE_URL${pokemonId.toString().padStart(3, '0')}.png",
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.surface,
                    highlightColor = Color.LightGray,
                    durationMillis = 400,
                    dropOff = 0.8f,
                    tilt = 20f
                )
            )
            Text(
                pokemonName,
                Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = typography.h5,
            )
        }
    }
}

@Composable
fun UnknownImage() {
    Image(
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = "",
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        modifier = Modifier
            .size(200.dp)
    )
}

@Preview
@Composable
fun PreviewPokemonCard() {
    PokemonTheme {
        PokemonCard("Bulbasaur", 1) {}
    }
}