package com.example.pokemon.ui.screen.pokemonlist

import androidx.compose.runtime.Composable
import com.example.pokemon.ui.screen.pokemonlist.components.PokemonList
import com.example.pokemon.utils.Intent
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.SendChannel

@Composable
fun PokemonListScreen(
    state: UIState?,
    event: UIEvent?,
    actionChannel: SendChannel<Intent>
) {
    state?.let {
        when(it) {
            is PokemonListState.PokemonList -> PokemonList(
                it.pagingDataFlow,
            ) { clickedPokemonId ->
                actionChannel.trySend(PokemonListIntent.ShowDetail(clickedPokemonId))
            }
        }
    }
}