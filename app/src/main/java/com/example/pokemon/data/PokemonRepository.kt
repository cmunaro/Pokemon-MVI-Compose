package com.example.pokemon.data

import androidx.paging.PagingData
import com.example.pokemon.data.domainmodel.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<PagingData<Pokemon>>
    suspend fun getPokemon(pokemonId: Int): Pokemon
}