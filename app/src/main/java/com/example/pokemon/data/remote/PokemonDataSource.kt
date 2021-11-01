package com.example.pokemon.data.remote

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.data.domainmodel.RemoteKey
import com.example.pokemon.data.domainmodel.toPokemon
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
        val page: Int = when (loadType) {
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state)
                ?.nextKey?.minus(state.config.pageSize)
                ?.coerceAtLeast(0) ?: 0
            LoadType.PREPEND -> getRemoteKeyForFirstItem(state)?.prevKey
                ?: return MediatorResult.Success(endOfPaginationReached = false)
            LoadType.APPEND -> getRemoteKeyForLastItem(state)?.nextKey
             ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

        val loadSize = state.config.pageSize
        try {
            val pokemonList = getPokemonList(page = page, loadSize = loadSize)
            val endOfPagination = pokemonList.size < loadSize
            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.clearAll()
                    remoteKeyDao.clearAll()
                }
                val prevKey = if (page == 0) null else page - loadSize
                val nextKey = if (endOfPagination) null else page + loadSize
                val keys = pokemonList.map {
                    RemoteKey(it.id, prevKey, nextKey)
                }
                remoteKeyDao.insertRemoteKeys(keys)
                pokemonDao.insertAll(pokemonList)
                Log.d("XENA", "PREV: $prevKey   NEXT: $nextKey")
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        } catch (t: Throwable) {
            return MediatorResult.Error(t)
        }
    }

    private suspend fun getPokemonList(page: Int, loadSize: Int): List<Pokemon> =
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Pokemon>): RemoteKey? {
        // When the db starts from empty, for same reason after the first page is loaded,
        // state.lastItemOrNull() returns null
        // https://stackoverflow.com/questions/66813622/android-paging-3-loadtype-append-returns-null-remote-keys
        // The workaround seems to be fetching the last key always from db instead of from the state
//        return state.lastItemOrNull()?.let { pokemon ->
//            db.withTransaction { remoteKeyDao.getRemoteKeys(pokemon.id) }
//        }
        return pokemonDatabase.withTransaction { remoteKeyDao.getLastKey() }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Pokemon>): RemoteKey? {
        return state.firstItemOrNull()?.let { pokemon ->
            pokemonDatabase.withTransaction { remoteKeyDao.getRemoteKeys(pokemon.id) }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Pokemon>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pokemonDatabase.withTransaction { remoteKeyDao.getRemoteKeys(id) }
            }
        }
    }
}
