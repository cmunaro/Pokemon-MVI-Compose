package com.example.pokemon.ui.screen.pokemondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.data.domainmodel.Type
import com.example.pokemon.ui.screen.pokemondetail.components.*
import com.example.pokemon.ui.theme.PokemonTheme
import io.uniflow.core.flow.data.UIState

@Composable
fun PokemonDetailScreen(
    state: UIState?,
    disableImageFetching: Boolean = false,
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
            PokemonHeader(pokemon, disableImageFetching)
            if (pokemon != null) {
                Spacer(modifier = Modifier.requiredHeight(8.dp))
                PokemonName(pokemon.name)
                Spacer(modifier = Modifier.requiredHeight(24.dp))
                PokemonTypeList(pokemon.types)
            }
            Spacer(modifier = Modifier.requiredHeight(32.dp))
            PokemonStats(pokemon?.stats)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    val pokemon = Pokemon(
        name = "Charmender",
        types = listOf(Type.DRAGON, Type.FIRE, Type.ELECTRIC, Type.STEEL)
    )
    val state = PokemonDetailState(pokemon = pokemon)
    PokemonTheme {
        PokemonDetailScreen(state, true) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreviewDark() {
    val pokemon = Pokemon(
        name = "Charmender",
        types = listOf(Type.DRAGON, Type.FIRE, Type.ELECTRIC, Type.STEEL)
    )
    val state = PokemonDetailState(pokemon = pokemon)
    PokemonTheme(darkTheme = true) {
        PokemonDetailScreen(state, true) {}
    }
}