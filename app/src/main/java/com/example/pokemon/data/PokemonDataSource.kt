package com.example.pokemon.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.model.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

private const val STARTING_OFFSET_INDEX = 0

class PokemonDataSource(
    private val pokemonApi: PokemonAPI
) : PagingSource<Int, PokemonResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResponse> =
        withContext(Dispatchers.IO) {
            val offset = params.key ?: STARTING_OFFSET_INDEX
            val loadSize = params.loadSize

            return@withContext try {
                val pokemonListResponse = pokemonApi.getPokemons(loadSize, offset)

                val pokemonInfo: List<PokemonResponse> = pokemonListResponse.pokemonList
                    .map {
                        val pokemonId = it.url
                            .removeSuffix("/")
                            .substringAfterLast("/")
                            .toInt()
                        async { pokemonApi.getPokemonInfo(pokemonId = pokemonId) }
                    }
                    .awaitAll()

                LoadResult.Page(
                    data = pokemonInfo,
                    prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                    nextKey = if (pokemonListResponse.next == null) null else offset + loadSize
                )
            } catch (throwable: Throwable) {
                LoadResult.Error(throwable)
            }
        }

    override fun getRefreshKey(state: PagingState<Int, PokemonResponse>): Int? =
        state.anchorPosition
}
