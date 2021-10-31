package com.example.pokemon.utils

import androidx.compose.ui.graphics.Color
import com.example.pokemon.ui.theme.*

object PokemonTypeUtils {
    fun getTypeColor(type: String): Color {
        return when (type.lowercase()) {
            "fighting" -> fighting
            "flying" -> flying
            "poison" -> poison
            "ground" -> ground
            "rock" -> rock
            "bug" -> bug
            "ghost" -> ghost
            "steel" -> steel
            "fire" -> fire
            "water" -> water
            "grass" -> grass
            "electric" -> electric
            "psychic" -> psychic
            "ice" -> ice
            "dragon" -> dragon
            "fairy" -> fairy
            "dark" -> dark
            else -> gray_21
        }
    }
}
