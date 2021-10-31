package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.ui.shared.PokemonImage
import com.example.pokemon.ui.shared.UnknownPokemonImage
import com.example.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonCard(
    pokemonName: String,
    pokemonId: Int,
    onClick: (pokemonId: Int) -> Unit,
    disableImageFetching: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(pokemonId) }
    ) {
        Column {
            if (disableImageFetching) {
                UnknownPokemonImage()
            } else {
                PokemonImage(pokemonId = pokemonId)
            }
            Text(
                pokemonName,
                Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = typography.h5,
            )
        }
    }
}

@Preview
@Composable
fun PreviewPokemonCard() {
    PokemonTheme {
        PokemonCard("Bulbasaur", 1, {}, disableImageFetching = true)
    }
}

@Preview
@Composable
fun PreviewPokemonCardDark() {
    PokemonTheme(darkTheme = true) {
        PokemonCard("Bulbasaur", 1, {}, disableImageFetching = true)
    }
}