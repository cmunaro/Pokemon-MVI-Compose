package com.example.pokemon.ui.screen.pokemonlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.utils.AndroidChanneledDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : AndroidChanneledDataFlow<PokemonListViewModel>() {

    init {
        viewModelScope.launch {
            val pokemon = repository.getPokemon()
            Log.d("XENA", pokemon.toString())
        }
    }

}

sealed class PokemonListState : UIState() {

}

sealed class PokemonListEvent : UIEvent() {

}