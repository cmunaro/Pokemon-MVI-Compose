package com.example.pokemon.ui.screen.pokemonlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.screen.pokemonlist.RequestFetchPokemons
import com.example.pokemon.ui.screen.pokemonlist.RequestShowDetail
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.utils.Intent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    pagingDataFlow: Flow<PagingData<Pokemon>>?,
    disableImageFetching: Boolean = false,  // Needed for a bug in GlideImage that makes it not load `previewPlaceholder`
    actionChannel: SendChannel<Intent>
) {

    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        state = rememberSwipeRefreshState(swipeRefreshState.isRefreshing),
        onRefresh = { actionChannel.trySend(RequestFetchPokemons) },
    ) {
        val lazyPokemons = pagingDataFlow?.collectAsLazyPagingItems()
        if (lazyPokemons == null) {
            Text(
                text = "There are no Pokemon!",
                modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
        } else {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                content = {
                    items(lazyPokemons.itemCount) { index ->
                        lazyPokemons[index]?.let {
                            PokemonCard(
                                pokemonName = it.name,
                                pokemonId = it.id,
                                disableImageFetching = disableImageFetching
                            ) { pokemonId ->
                                actionChannel.trySend(RequestShowDetail(pokemonId))
                            }
                        }
                    }
                    swipeRefreshState.isRefreshing = false
                }
            )
        }
    }
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
    runBlocking {
        pagingFlow.emit(pagingData)
    }

    PokemonTheme {
        PokemonList(
            pagingDataFlow = pagingFlow,
            disableImageFetching = true,
            actionChannel = Channel()
        )
    }
}