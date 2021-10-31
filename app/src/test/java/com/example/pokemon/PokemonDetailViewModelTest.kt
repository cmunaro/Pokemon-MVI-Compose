package com.example.pokemon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import app.cash.turbine.test
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.screen.pokemondetail.PokemonDetailState
import com.example.pokemon.ui.screen.pokemondetail.PokemonDetailViewModel
import com.example.pokemon.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.livedata.states
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {
    private val pokemonRepository = mockk<PokemonRepository>()
    private lateinit var viewmodel: PokemonDetailViewModel
    private val dummyPokemon = Pokemon()
    private lateinit var dummyPokemonAnswer: () -> Pokemon

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        every { runBlocking { pokemonRepository.getPokemon(any()) } } answers { dummyPokemonAnswer() }
        viewmodel = PokemonDetailViewModel(pokemonRepository)
        viewmodel.routerChannel = Channel()
    }

    @Test
    fun `load pokemon when pokemonId is set`() = runBlocking {
        viewmodel.states.asFlow().test {
            dummyPokemonAnswer = { dummyPokemon }
            viewmodel.pokemonId = 123

            val state = awaitItem()

            assertThat(state).isInstanceOf(PokemonDetailState::class.java)
            state as PokemonDetailState
            assertThat(state.isLoading).isFalse()
            assertThat(state.pokemon).isEqualTo(dummyPokemon)
            assertThat(state.error).isFalse()
        }
    }

    @Test
    fun `fail to load pokemon`() = runBlocking {
        viewmodel.states.asFlow().test {
            dummyPokemonAnswer = { throw Exception() }
            viewmodel.pokemonId = 123

            val state = awaitItem()

            assertThat(state).isInstanceOf(PokemonDetailState::class.java)
            state as PokemonDetailState
            assertThat(state.isLoading).isFalse()
            assertThat(state.pokemon).isNull()
            assertThat(state.error).isTrue()
        }
    }
}