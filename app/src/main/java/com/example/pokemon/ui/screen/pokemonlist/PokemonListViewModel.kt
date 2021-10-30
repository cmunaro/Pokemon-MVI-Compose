package com.example.pokemon.ui.screen.pokemonlist

import androidx.lifecycle.viewModelScope
import com.example.pokemon.utils.AndroidChanneledDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor() :
    AndroidChanneledDataFlow<PokemonListViewModel>() {

    init {
        viewModelScope.launch {
            ticker(2000, 2000)
                .consumeEach {
                    publishEvent(PokemonListEvent.OrcaLoca())
                }
        }
    }

    fun loadMessage() = action {
        setState(PokemonListState.Message(message = UUID.randomUUID().toString()))
    }

    override suspend fun onError(error: Exception, currentState: UIState) {}
}


sealed class PokemonListState : UIState() {
    data class Message(
        val message: String
    ) : PokemonListState()
}

sealed class PokemonListEvent : UIEvent() {
    class OrcaLoca : PokemonListEvent()
}