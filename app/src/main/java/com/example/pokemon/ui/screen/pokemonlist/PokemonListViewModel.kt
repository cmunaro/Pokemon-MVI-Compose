package com.example.pokemon.ui.screen.pokemonlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pokemon.Route
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.model.PokemonResponse
import com.example.pokemon.utils.AndroidIntentDataFlow
import com.example.pokemon.utils.Intent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : AndroidIntentDataFlow() {

    override val intentHandler: ((Intent) -> Unit) = { intent ->
        when (intent) {
            is PokemonListIntent.ShowDetail -> showDetailsOf(intent.pokemonId)
        }
    }

    init {
        viewModelScope.launch { getPokemons() }
    }

    private fun getPokemons() = action {
        setState {
            PokemonListState.PokemonList(repository.getPokemons())
        }
    }

    private fun showDetailsOf(pokemonId: Int) = action {
        navigateTo(Route.PokemonDetail.apply {
            this.pokemonId = pokemonId
        })
    }
}

sealed class PokemonListState : UIState() {
    data class PokemonList(
        val pagingDataFlow: Flow<PagingData<PokemonResponse>>
    ) : PokemonListState()
}

sealed interface PokemonListIntent : Intent {
    data class ShowDetail(val pokemonId: Int) : PokemonListIntent
}