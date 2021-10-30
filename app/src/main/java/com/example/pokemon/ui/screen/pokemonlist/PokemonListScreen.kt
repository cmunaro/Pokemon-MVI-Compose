package com.example.pokemon.ui.screen.pokemonlist

import androidx.compose.runtime.Composable
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.SendChannel
import kotlin.reflect.KFunction1

@Composable
fun PokemonListScreen(
    state: UIState?,
    event: UIEvent?,
    actionChannel: SendChannel<KFunction1<PokemonListViewModel, *>>
) {
    event?.let {
        when (it) {
            is PokemonListEvent.ReadyToFetch -> actionChannel.trySend(PokemonListViewModel::getPokemons)
            else -> {
            }
        }
    }


}