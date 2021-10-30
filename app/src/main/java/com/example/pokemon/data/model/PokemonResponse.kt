package com.example.pokemon.data.model

import com.squareup.moshi.Json

data class PokemonResponse(
    var id: Int = 0,
    var name: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var abilities: List<AbilityResponse> = emptyList(),
    var stats: List<StatResponse> = emptyList(),
    var types: List<TypeResponse> = emptyList()
)

data class AbilityResponse(
    val ability: AbilityInfoResponse,
    @Json(name = "is_hidden") val isHidden: Boolean,
    val slot: Int
)

data class AbilityInfoResponse(
    val name: String
)

data class StatResponse(
    @Json(name ="base_stat") val baseStat: Int,
    val effort: Int,
    val stat: StatInfoResponse
)

data class StatInfoResponse(
    val name: String
)

class TypeResponse(
    val slot: Int,
    val type: TypeInfoResponse
)

data class TypeInfoResponse(
    val name: String
)