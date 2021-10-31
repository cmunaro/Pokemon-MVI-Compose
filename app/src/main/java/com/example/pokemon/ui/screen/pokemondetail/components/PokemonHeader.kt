package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.shared.PokemonImage
import com.example.pokemon.ui.shared.UnknownPokemonImage

@Composable
fun PokemonHeader(pokemon: Pokemon?, disableImageFetching: Boolean = false) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(0, 0, 25, 25))
                .background(Color.Red)
        ) {
            if (pokemon == null || disableImageFetching) {
                UnknownPokemonImage(modifier = Modifier.align(Alignment.Center))
            } else {
                PokemonImage(
                    pokemonId = pokemon.id,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
        }
    }
}