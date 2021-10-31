package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun PokemonName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h4.copy(
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    )
}