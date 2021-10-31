package com.example.pokemon

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.paging.PagingData
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.ui.screen.pokemonlist.PokemonListScreen
import com.example.pokemon.ui.screen.pokemonlist.PokemonListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class PokemonListInstrumentationTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun checkLoadedCards() {
        val dummyPokemonList = listOf(
            Pokemon(name = "bulbasaur"),
            Pokemon(name = "ivysaur"),
            Pokemon(name = "venusaur"),
            Pokemon(name = "charmender"),
            Pokemon(name = "charmeleon"),
            Pokemon(name = "charizard"),
        )
        val pokemonListState = PokemonListState.PokemonList(
            flowOf(PagingData.from(dummyPokemonList))
        )

        composeTestRule.setContent {
            PokemonListScreen(state = pokemonListState, Channel(), disableImageFetching = true)
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("ivysaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("charizard").performScrollTo().assertIsDisplayed()
    }
}