package com.example.pokemon.utils

object PokemonImageUtil {
    private const val POKEMON_IMAGE_BASE_URL =
        "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/"

    fun getImageUrlOf(pokemonId: Int): String {
        val pokemonStringId = pokemonId.toString()
            .padStart(3, '0')
        return "$POKEMON_IMAGE_BASE_URL$pokemonStringId.png"
    }
}