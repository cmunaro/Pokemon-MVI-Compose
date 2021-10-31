package com.example.pokemon.data.remote

import androidx.paging.*
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.pokemon.data.local.PokemonDao
import com.example.pokemon.data.local.PokemonDatabase
import com.example.pokemon.data.local.RemoteKeyDao
import com.example.pokemon.data.model.*
import kotlinx.coroutines.*
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonDataSource(
    private val pokemonApi: PokemonAPI,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, Pokemon>() {
    private val pokemonDao: PokemonDao = pokemonDatabase.pokemonDao()
    private val remoteKeyDao: RemoteKeyDao = pokemonDatabase.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        val page: Int = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }
        val loadSize = if (page == 0) state.config.initialLoadSize else state.config.pageSize
        try {
            val pokemons: List<Pokemon> = getPokemons(loadSize, page)
            val endOfList = pokemons.isEmpty()
            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    pokemonDao.clearAll()
                }
                val prevKey = if (page == 0) null else page - loadSize
                val nextKey = if (endOfList) null else page + loadSize
                val keys = pokemons.map {
                    RemoteKey(it.id, prevKey, nextKey)
                }
                remoteKeyDao.insertRemote(keys)
                pokemonDao.insertAll(pokemons)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getPokemons(loadSize: Int, page: Int): List<Pokemon> =
        withContext(Dispatchers.IO) {
            val pokemonListResponse: PokemonListResponse =
                pokemonApi.getPokemons(limit = loadSize, offset = page)
            val pokemons: List<Pokemon> = pokemonListResponse.pokemonList
                .map {
                    val pokemonId = it.url
                        .removeSuffix("/")
                        .substringAfterLast("/")
                        .toInt()
                    async { pokemonApi.getPokemonInfo(pokemonId = pokemonId) }
                }
                .awaitAll()
                .map { it.toPokemon() }
            return@withContext pokemons
        }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Pokemon>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(state.config.pageSize) ?: 0
            }
            LoadType.PREPEND -> {
                getFirstRemoteKey(state)?.prevKey
                    ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                getLastRemoteKey(state)?.nextKey
                    ?: MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Pokemon>): RemoteKey? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { remoteKeyDao.getRemoteKeys(it.id) }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Pokemon>): RemoteKey? {
        return withContext(Dispatchers.IO) {
            state.pages
                .lastOrNull { it.data.isNotEmpty() }
                ?.data?.lastOrNull()
                ?.let { remoteKeyDao.getRemoteKeys(it.id) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, Pokemon>): RemoteKey? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id
                    ?.let { remoteKeyDao.getRemoteKeys(it) }
            }
        }
    }
}
