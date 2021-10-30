package com.example.pokemon.ui.screen.pokemonlist

import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import io.uniflow.core.threading.launchOnMain
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlin.reflect.KFunction1

@Composable
fun PokemonListScreen(
    state: UIState?,
    event: UIEvent?,
    actionChannel: SendChannel<KFunction1<PokemonListViewModel, *>>
) {
    val context = LocalContext.current
    event?.let {
        Toast.makeText(context, "OPPALE", Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(true) {
        launchOnMain {
            while (true) {
                delay(2000)
                actionChannel.send(PokemonListViewModel::loadMessage)
            }
        }
    }

    state?.let {
        when (it) {
            is PokemonListState.Message -> Text(text = it.message)
        }
    }
}