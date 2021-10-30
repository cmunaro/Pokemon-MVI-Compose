package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.data.model.PokemonResult
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(pagingDataFlow: Flow<PagingData<PokemonResult>>) {
    val lazyPokemons = pagingDataFlow.collectAsLazyPagingItems()
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(lazyPokemons.itemCount) { index ->
                lazyPokemons[index]?.let {
                    PokemonCard(it.name)
                }
            }
        }
    )
}