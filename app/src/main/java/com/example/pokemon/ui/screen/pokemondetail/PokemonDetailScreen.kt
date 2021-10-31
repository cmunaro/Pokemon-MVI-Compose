package com.example.pokemon.ui.screen.pokemondetail

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonDetailScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Charmender") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) {
        Text("DETIALS")
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    PokemonTheme {
        PokemonDetailScreen {}
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreviewDark() {
    PokemonTheme(darkTheme = true) {
        PokemonDetailScreen {}
    }
}