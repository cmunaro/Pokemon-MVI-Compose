package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.data.domainmodel.Type
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.theme.white_12
import com.example.pokemon.utils.PokemonTypeUtils

@Composable
fun PokemonTypeChip(type: Type, modifier: Modifier = Modifier) {
    Text(
        text = type.name.lowercase(),
        style = MaterialTheme.typography.h6.copy(
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = white_12
        ),
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(PokemonTypeUtils.getTypeColor(type.name))
            .width(100.dp)
            .padding(top = 8.dp, bottom = 10.dp)
    )
}

@Preview
@Composable
fun PokemonTypeChipPreview() {
    PokemonTheme {
        PokemonTypeChip(Type.FIRE)
    }
}
