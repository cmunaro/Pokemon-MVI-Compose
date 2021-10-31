package com.example.pokemon.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pokemon.Route
import io.uniflow.android.AndroidDataFlow
import io.uniflow.android.livedata.LiveDataPublisher
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import io.uniflow.core.flow.onState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

abstract class AndroidIntentDataFlow(defaultState: UIState = UIState.Empty) :
    AndroidDataFlow(defaultState = defaultState) {

    private val _intentChannel = Channel<Intent>()
    val intentChannel: SendChannel<Intent> = _intentChannel
    val events: LiveData<UIEvent> = (defaultDataPublisher as LiveDataPublisher).events
        .map { it.content }
    open val intentHandler: ((Intent) -> Unit)? = null
    var routerChannel: Channel<Route>? = null

    init {
        viewModelScope.launch {
            _intentChannel.consumeEach { intent ->
                intentHandler?.invoke(intent)
            }
        }
    }

    protected suspend fun navigateTo(route: Route) {
        routerChannel?.send(route)
    }

    protected suspend inline fun <reified T : UIState> alterState(
        crossinline stateReducer: T.() -> T
    ) {
        onState<T> { currentState ->
            setState { stateReducer(currentState) }
        }
    }
}