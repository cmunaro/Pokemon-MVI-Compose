package com.example.pokemon.ui.screen.pokemonlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.model.PokemonResponse
import com.example.pokemon.utils.AndroidChanneledDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : AndroidChanneledDataFlow<PokemonListViewModel>() {

    init {
        viewModelScope.launch {
            getPokemons()
        }
    }

    fun getPokemons() = action {
        setState {
            PokemonListState.PokemonList(repository.getPokemons())
        }
    }
}

sealed class PokemonListState : UIState() {
    data class PokemonList(
        val pagingDataFlow: Flow<PagingData<PokemonResponse>>
    ) : PokemonListState()
}

sealed class PokemonListEvent : UIEvent() {
}