package com.example.pokemon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemon.ui.screen.pokemonlist.PokemonListScreen
import com.example.pokemon.ui.screen.pokemonlist.PokemonListViewModel
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

@Composable
fun PokemonNavController(navController: NavHostController) = NavHost(
    navController = navController,
    startDestination = Route.PokemonList.path
) {
    composable(Route.PokemonList.path) {
        val viewModel: PokemonListViewModel = hiltViewModel()

        val state: UIState? by viewModel.states.observeAsState()
        val event: UIEvent? by viewModel.events.observeAsState()

        PokemonListScreen(state, event, viewModel.intentChannel)
    }
}

sealed class Route(val path: String) {
    object PokemonList : Route("pokemonList")
}