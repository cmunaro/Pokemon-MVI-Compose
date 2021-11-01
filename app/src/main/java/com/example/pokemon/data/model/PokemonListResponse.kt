package com.example.pokemon.data.model

import com.squareup.moshi.Json

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    @Json(name = "results") val pokemonList: List<PokemonResult>
)