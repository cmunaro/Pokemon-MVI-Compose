package com.example.pokemon.ui.screen

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor() : AndroidDataFlow() {
    init {
        viewModelScope.launch {
            ticker(3000, 3000)
                .consumeEach {
                    sendEvent(PokemonListEvent.OrcaLoca)
                }
        }
    }

    fun loadMessage() = action {
        delay(2000)
        setState(PokemonListState.Message("Hello World"))
    }

    override suspend fun onError(error: Exception, currentState: UIState) {}
}

sealed class PokemonListState: UIState() {
    data class Message(
        val message: String
    ): PokemonListState()
}

sealed class PokemonListEvent: UIEvent() {
    object OrcaLoca: PokemonListEvent()
}