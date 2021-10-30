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

abstract class AndroidIntentDataFlow : AndroidDataFlow() {
    private val _intentChannel = Channel<Intent>()
    val intentChannel: SendChannel<Intent> = _intentChannel
    val events: LiveData<UIEvent> = (defaultDataPublisher as LiveDataPublisher).events
        .map { it.content }

    open val intentHandler: ((Intent) -> Unit)? = null

    init {
        viewModelScope.launch {
            _intentChannel.consumeEach { intent ->
                intentHandler?.invoke(intent)
            }
        }
    }
}