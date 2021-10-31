package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.R
import com.example.pokemon.ui.theme.PokemonTheme

@Composable
fun UnknownPokemonImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = stringResource(R.string.content_description_unknown_pokemon_image),
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
    )
}

@Preview
@Composable
fun PreviewUnknownPokemonImage() {
    PokemonTheme(darkTheme = false) {
        UnknownPokemonImage()
    }
}

@Preview
@Composable
fun PreviewUnknownPokemonImageDark() {
    PokemonTheme(darkTheme = true) {
        UnknownPokemonImage()
    }
}