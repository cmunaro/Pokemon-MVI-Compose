package com.example.pokemon.ui.screen.pokemonlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.utils.AndroidChanneledDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : AndroidChanneledDataFlow<PokemonListViewModel>() {

    init {
        viewModelScope.launch {
            sendEvent(PokemonListEvent.ReadyToFetch)
        }
    }

    fun getPokemons() = action {
        repository.getPokemons()
            .collect {
                Log.d("XENA", it.map { it.name }.toString())
            }
    }
}

sealed class PokemonListState : UIState() {

}

sealed class PokemonListEvent : UIEvent() {
    object ReadyToFetch: PokemonListEvent()
}