package com.example.pokemon.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.data.local.PokemonDatabase
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.data.remote.PokemonAPI
import com.example.pokemon.data.remote.PokemonDataSource
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val pokemonAPI: PokemonAPI,
    private val pokemonDatabase: PokemonDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = 20),
            pagingSourceFactory = { pokemonDatabase.pokemonDao().pagingSource() },
            remoteMediator = PokemonDataSource(pokemonAPI, pokemonDatabase)
        ).flow
    }
}