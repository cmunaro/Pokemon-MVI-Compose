package com.example.pokemon.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.pokemon.Route
import com.example.pokemon.utils.events
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.Event
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    navController: NavHostController
) {
    val state: UIState? by viewModel.states.observeAsState()
    val event: Event<UIEvent>? by viewModel.events.observeAsState()

    val context = LocalContext.current
    LocalLifecycleOwner.current.also { lifecycleOwner ->
        LaunchedEffect(true) {
            Log.d("XENA", "SCREEN $lifecycleOwner ${lifecycleOwner.lifecycle} ${lifecycleOwner.lifecycleScope}")
            lifecycleOwner.onEvents(viewModel) {
                when (it) {
                    is PokemonListEvent.OrcaLoca -> {
                        Toast.makeText(context, "Orca Loca! $event", Toast.LENGTH_SHORT).show()
                        navController.navigate(Route.Test.path)
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.loadMessage()
    }

    state?.let {
        when (it) {
            is PokemonListState.Message -> Text(text = it.message)
        }
    }
}