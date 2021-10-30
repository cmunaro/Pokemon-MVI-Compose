package com.example.pokemon.data

import com.example.pokemon.data.model.PokemonListResponse

class PokemonRepository(private val pokemonAPI: PokemonAPI) {

    suspend fun getPokemon(): PokemonListResponse {
        return pokemonAPI.getPokemona(0, 10)
    }
}