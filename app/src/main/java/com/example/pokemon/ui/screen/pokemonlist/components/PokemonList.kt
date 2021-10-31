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
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.ui.theme.PokemonTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    pagingDataFlow: Flow<PagingData<Pokemon>>,
    isPreview: Boolean = false,  // Needed for a bug in GlideImage that makes it not load `previewPlaceholder`
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
                    PokemonCard(it.name, it.id, onPokemonClick, isPreview)
                }
            }
        }
    )
}


@Preview
@Composable
fun PokemonListPreview() {
    val pagingFlow = MutableSharedFlow<PagingData<Pokemon>>()
    val pagingData = PagingData.from(
        listOf(
            Pokemon(name = "bulbasaur"),
            Pokemon(name = "ivysaur"),
            Pokemon(name = "venusaur"),
            Pokemon(name = "charmender"),
            Pokemon(name = "charmeleon"),
            Pokemon(name = "charizard"),
        )
    )

    PokemonTheme {
        PokemonList(
            pagingDataFlow = pagingFlow,
            isPreview = true
        ) {}
    }

    runBlocking {
        pagingFlow.emit(pagingData)
    }
}