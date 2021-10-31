package com.example.pokemon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemon.ui.screen.pokemondetail.PokemonDetailScreen
import com.example.pokemon.ui.screen.pokemondetail.PokemonDetailViewModel
import com.example.pokemon.ui.screen.pokemonlist.PokemonListScreen
import com.example.pokemon.ui.screen.pokemonlist.PokemonListViewModel
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.properties.Delegates

@Composable
fun PokemonNavController(navController: NavHostController, routerChannel: Channel<Route>) = NavHost(
    navController = navController,
    startDestination = Route.PokemonList.path,
) {
    composable(Route.PokemonList.path) {
        val viewModel: PokemonListViewModel = hiltViewModel()
        LaunchedEffect(routerChannel) { viewModel.routerChannel = routerChannel }
        val state: UIState? by viewModel.states.observeAsState()
        val event: UIEvent? by viewModel.events.observeAsState()

        PokemonListScreen(state, event, viewModel.intentChannel)
    }

    composable(Route.PokemonDetail.path) {
        val pokemonId = remember { it.arguments?.getString("id") ?: return@composable }
        val viewModel: PokemonDetailViewModel = hiltViewModel()
        LaunchedEffect(pokemonId) { viewModel.pokemonId = pokemonId.toInt() }
        val state: UIState? by viewModel.states.observeAsState()

        PokemonDetailScreen(state) {
            navController.popBackStack()
        }
    }
}

sealed class Route(val path: String) {
    open val navigationPath = path

    object PokemonList : Route("pokemonList")
    object PokemonDetail : Route("pokemon/{id}") {
        override val navigationPath: String
            get() = path.replace("{id}", pokemonId.toString())
        var pokemonId by Delegates.notNull<Int>()
    }
}