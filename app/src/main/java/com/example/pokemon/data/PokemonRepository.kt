package com.example.pokemon.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.data.model.PokemonResult
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val pokemonAPI: PokemonAPI) {

    fun getPokemons(): Flow<PagingData<PokemonResult>> = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            PokemonDataSource(pokemonAPI)
        }
    ).flow
}