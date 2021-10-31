package com.example.pokemon

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.ui.theme.PokemonTheme
import io.uniflow.core.threading.launchOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach

@Composable
fun PokemonComposeApplication() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val routerChannel = setupRouter(scope, navController)

    PokemonTheme {
        Surface(color = MaterialTheme.colors.background) {
            PokemonNavController(
                routerChannel = routerChannel,
                navController = navController
            )
        }
    }
}

private fun setupRouter(
    scope: CoroutineScope,
    navController: NavHostController
): Channel<Route> {
    val routerChannel = Channel<Route>()
    scope.launchOnMain {
        routerChannel.consumeEach {
            navController.navigate(it.navigationPath)
        }
    }
    return routerChannel
}