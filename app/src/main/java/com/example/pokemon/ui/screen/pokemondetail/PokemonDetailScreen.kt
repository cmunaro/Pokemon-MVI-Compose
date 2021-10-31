package com.example.pokemon.ui.screen.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.ui.screen.pokemonlist.components.UnknownPokemonImage
import com.example.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonDetailScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Pokedex") },
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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface {
                val shape = GenericShape { size, _ ->
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height * 0.7f)
                    lineTo(0f, 0f)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(0, 0, 25, 25))
                        .background(Color.Red)
                ) {
                    UnknownPokemonImage(modifier = Modifier.align(Alignment.Center))
                }
            }
            Text(
                text = "Charmender",
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )
        }
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