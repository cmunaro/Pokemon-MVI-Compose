package com.example.pokemon.data

import com.example.pokemon.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon/")
    suspend fun getPokemona(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

}