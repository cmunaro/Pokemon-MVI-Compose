package com.example.pokemon.ui.screen.pokemonlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.Route
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.domainmodel.Pokemon
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
            is RequestShowDetail -> showDetailsOf(intent.pokemonId)
            is RequestFetchPokemons -> getPokemons()
        }
    }

    init {
        viewModelScope.launch { getPokemons() }
    }

    private fun getPokemons() = action {
        setState {
            PokemonListState.PokemonList(repository.getPokemons().cachedIn(viewModelScope))
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
        val pagingDataFlow: Flow<PagingData<Pokemon>>?
    ) : PokemonListState()
}

data class RequestShowDetail(val pokemonId: Int) : Intent
object RequestFetchPokemons : Intent
