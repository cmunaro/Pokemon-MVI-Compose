package com.example.pokemon.ui.screen.pokemonlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.ui.screen.pokemonlist.components.PokemonList
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.utils.Intent
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.flowOf

@Composable
fun PokemonListScreen(
    state: UIState?,
    event: UIEvent?,
    actionChannel: SendChannel<Intent>
) {
    state?.let {
        when(it) {
            is PokemonListState.PokemonList -> PokemonList(
                pagingDataFlow = it.pagingDataFlow,
            ) { clickedPokemonId ->
                actionChannel.trySend(PokemonListIntent.ShowDetail(clickedPokemonId))
            }
        }
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {
    val dummyPokemonList = listOf(
        Pokemon(name = "bulbasaur"),
        Pokemon(name = "ivysaur"),
        Pokemon(name = "venusaur"),
        Pokemon(name = "charmender"),
        Pokemon(name = "charmeleon"),
        Pokemon(name = "charizard"),
    )
    val pokemonListState = PokemonListState.PokemonList(
        flowOf(PagingData.from(dummyPokemonList))
    )

    PokemonTheme {
        PokemonListScreen(
            state = pokemonListState,
            event = null, 
            actionChannel = Channel()
        )
    }
}