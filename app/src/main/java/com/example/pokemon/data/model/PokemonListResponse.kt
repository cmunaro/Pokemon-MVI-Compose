package com.example.pokemon.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    @Json(name = "results") val pokemonList: List<PokemonResult>
) : Parcelable