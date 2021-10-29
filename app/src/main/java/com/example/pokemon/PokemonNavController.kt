package com.example.pokemon

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemon.ui.screen.PokemonListScreen
import com.example.pokemon.ui.screen.PokemonListViewModel
import com.example.pokemon.ui.screen.Test

@Composable
fun PokemonNavController(navController: NavHostController) = NavHost(
    navController = navController,
    startDestination = Route.PokemonList.path
) {
    composable(Route.PokemonList.path) {
        val viewModel: PokemonListViewModel = viewModel()
        PokemonListScreen(
            viewModel,
            navController = navController
        )
    }
    composable(Route.Test.path) {
        Test(navController)
    }
}

sealed class Route(val path: String) {
    object PokemonList : Route("pokemonList")
    object Test: Route("test")
}