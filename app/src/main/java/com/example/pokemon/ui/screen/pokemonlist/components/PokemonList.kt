package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    pagingDataFlow: Flow<PagingData<Pokemon>>,
    onPokemonClick: (pokemonId: Int) -> Unit
) {
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()

    val lazyPokemons = pagingDataFlow.collectAsLazyPagingItems()
    LazyVerticalGrid(
        modifier = Modifier.scrollable(scrollState, Orientation.Vertical),
        state = listState,
        cells = GridCells.Fixed(2),
        content = {
            items(lazyPokemons.itemCount) { index ->
                lazyPokemons[index]?.let {
                    PokemonCard(it.name, it.id, onPokemonClick)
                }
            }
        }
    )
}