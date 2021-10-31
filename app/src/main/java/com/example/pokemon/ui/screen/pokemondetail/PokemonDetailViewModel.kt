package com.example.pokemon.ui.screen.pokemondetail

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.utils.AndroidIntentDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.core.flow.data.UIState
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : AndroidIntentDataFlow(defaultState = PokemonDetailState(isLoading = true)) {

    var pokemonId: Int = 0
        set(value) {
            field = value
            loadPokemonInfo(value)
        }

    private fun loadPokemonInfo(pokemonId: Int) = action {
        try {
            val pokemon = repository.getPokemon(pokemonId)
            setState { PokemonDetailState(isLoading = false, pokemon = pokemon, error = false) }
        } catch (exception: Exception) {
            alterState<PokemonDetailState> { copy(isLoading = false, error = true) }
        }
    }
}

data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: Pokemon? = null,
    val error: Boolean = false
) : UIState()