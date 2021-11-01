package com.example.pokemon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.screen.pokemonlist.PokemonListState
import com.example.pokemon.ui.screen.pokemonlist.PokemonListViewModel
import com.example.pokemon.ui.screen.pokemonlist.RequestShowDetail
import com.example.pokemon.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.livedata.states
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListStateViewModelTest {
    private val pokemonRepository = mockk<PokemonRepository>()
    private lateinit var viewmodel: PokemonListViewModel

    private val dummyPokemonList = listOf(
        Pokemon(name = "bulbasaur"),
        Pokemon(name = "ivysaur"),
        Pokemon(name = "venusaur"),
        Pokemon(name = "charmender"),
        Pokemon(name = "charmeleon"),
        Pokemon(name = "charizard"),
    )
    private val paginatedPokemonList = PagingData.from(dummyPokemonList)
    private val flowOfPaginatedPokemon = MutableStateFlow(paginatedPokemonList)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        every { pokemonRepository.getPokemons() } returns flowOfPaginatedPokemon
        viewmodel = PokemonListViewModel(pokemonRepository)
        viewmodel.routerChannel = Channel()
    }

    @Test
    fun `automatically fetch pokemons`() = runBlocking {
        viewmodel.states.asFlow().test {
            val state = awaitItem()

            assertThat(state).isInstanceOf(PokemonListState::class.java)
            state as PokemonListState
            assertThat(state.isLoading).isFalse()

            expectNoEvents()
        }
    }

    @Test
    fun `go to details page`() = runBlocking {
        viewmodel.routerChannel!!.consumeAsFlow().test() {
            val pokemonId = 123
            viewmodel.intentChannel.send(RequestShowDetail(pokemonId))

            val route = awaitItem()

            assertThat(route).isInstanceOf(Route.PokemonDetail::class.java)
            route as Route.PokemonDetail
            assertThat(route.pokemonId).isEqualTo(pokemonId)
        }
    }
}