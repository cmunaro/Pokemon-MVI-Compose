package com.example.pokemon.utils

import androidx.lifecycle.LiveData
import io.uniflow.android.AndroidDataFlow
import io.uniflow.android.livedata.LiveDataPublisher
import io.uniflow.core.flow.data.Event
import io.uniflow.core.flow.data.UIEvent

val AndroidDataFlow.events: LiveData<Event<UIEvent>>
    get() = (defaultDataPublisher as LiveDataPublisher).events