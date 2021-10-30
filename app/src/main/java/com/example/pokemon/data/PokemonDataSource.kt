package com.example.pokemon.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.model.PokemonResult

private const val STARTING_OFFSET_INDEX = 0

class PokemonDataSource(
    private val pokemonApi: PokemonAPI
) : PagingSource<Int, PokemonResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val offset = params.key ?: STARTING_OFFSET_INDEX
        val loadSize = params.loadSize

        return try {
            val pokemonListResponse = pokemonApi.getPokemons(loadSize, offset)

            LoadResult.Page(
                data = pokemonListResponse.pokemonList,
                prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                nextKey = if (pokemonListResponse.next == null) null else offset + loadSize
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? =
        state.anchorPosition
}
