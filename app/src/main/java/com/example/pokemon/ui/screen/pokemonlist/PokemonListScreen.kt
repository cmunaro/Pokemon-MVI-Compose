package com.example.pokemon.ui.screen.pokemonlist

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.screen.pokemonlist.components.PokemonList
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.utils.Intent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.flowOf

@Composable
fun PokemonListScreen(
    state: UIState?,
    actionChannel: SendChannel<Intent>,
    disableImageFetching: Boolean = false
) {
    Scaffold(topBar = {
        TopAppBar(
        title = { Text(text = "Pokedex") }
    ) }) {
        state?.let {
            when(it) {
                is PokemonListState -> PokemonList(
                    pagingDataFlow = it.pagingDataFlow,
                    disableImageFetching = disableImageFetching,
                    actionChannel = actionChannel
                )
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
    val pokemonListState = PokemonListState(
        flowOf(PagingData.from(dummyPokemonList))
    )

    PokemonTheme {
        PokemonListScreen(
            state = pokemonListState,
            actionChannel = Channel()
        )
    }
}