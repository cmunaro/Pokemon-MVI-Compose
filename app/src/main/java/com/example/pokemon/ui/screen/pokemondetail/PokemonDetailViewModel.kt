package com.example.pokemon.ui.screen.pokemondetail

import android.util.Log
import com.example.pokemon.utils.AndroidIntentDataFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(): AndroidIntentDataFlow() {
    var pokemonId: String = ""
        set(value) {
            field = value
            Log.d("XENA", "Set pokemonId: $value")
        }
}