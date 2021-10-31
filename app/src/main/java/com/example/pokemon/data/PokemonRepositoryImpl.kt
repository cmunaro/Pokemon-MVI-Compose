package com.example.pokemon.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.data.domainmodel.toPokemon
import com.example.pokemon.data.local.PokemonDatabase
import com.example.pokemon.data.remote.PokemonAPI
import com.example.pokemon.data.remote.PokemonDataSource
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val pokemonAPI: PokemonAPI,
    private val pokemonDatabase: PokemonDatabase
) : PokemonRepository {
    private val pokemonDao = pokemonDatabase.pokemonDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = 20),
            pagingSourceFactory = { pokemonDao.pagingSource() },
            remoteMediator = PokemonDataSource(pokemonAPI, pokemonDatabase)
        ).flow
    }

    override suspend fun getPokemon(pokemonId: Int): Pokemon {
        var pokemon = pokemonDao.getPokemon(pokemonId)
        if (pokemon == null) {
            pokemon = pokemonAPI.getPokemonInfo(pokemonId)
                .toPokemon()
            pokemonDao.insert(pokemon)
        }
        return pokemon
    }
}