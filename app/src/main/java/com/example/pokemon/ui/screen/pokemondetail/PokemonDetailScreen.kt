package com.example.pokemon.ui.screen.pokemondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.screen.pokemondetail.components.PokemonDetailTopAppBar
import com.example.pokemon.ui.screen.pokemondetail.components.PokemonHeader
import com.example.pokemon.ui.screen.pokemondetail.components.PokemonName
import com.example.pokemon.ui.theme.PokemonTheme
import io.uniflow.core.flow.data.UIState

@Composable
fun PokemonDetailScreen(
    state: UIState?,
    isPreview: Boolean = false,
    onBack: () -> Unit
) {
    val pokemon = remember(state) { (state as? PokemonDetailState)?.pokemon }

    Scaffold(
        topBar = {
            PokemonDetailTopAppBar(onBack)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonHeader(pokemon, isPreview)
            if (pokemon != null) PokemonName(pokemon.name)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    val pokemon = Pokemon(name = "Charmender")
    val state = PokemonDetailState(pokemon = pokemon)
    PokemonTheme {
        PokemonDetailScreen(state, true) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreviewDark() {
    val pokemon = Pokemon(name = "Charmender")
    val state = PokemonDetailState(pokemon = pokemon)
    PokemonTheme(darkTheme = true) {
        PokemonDetailScreen(state, true) {}
    }
}