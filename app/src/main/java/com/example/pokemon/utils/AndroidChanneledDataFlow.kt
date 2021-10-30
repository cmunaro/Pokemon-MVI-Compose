package com.example.pokemon.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.uniflow.android.AndroidDataFlow
import io.uniflow.android.livedata.LiveDataPublisher
import io.uniflow.core.flow.data.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

abstract class AndroidChanneledDataFlow<VM> : AndroidDataFlow() {
    private val _actionChannel = Channel<KFunction1<VM, *>>()
    val actionChannel: SendChannel<KFunction1<VM, *>> = _actionChannel
    val events: LiveData<UIEvent> = (defaultDataPublisher as LiveDataPublisher).events
        .map { it.content }

    init {
        viewModelScope.launch {
            _actionChannel.consumeEach { action ->
                @Suppress("UNCHECKED_CAST")
                (this@AndroidChanneledDataFlow as? VM)
                    ?.let { viewModel -> action.invoke(viewModel) }
            }
        }
    }
}