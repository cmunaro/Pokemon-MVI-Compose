package com.example.pokemon

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.pokemon.data.domainmodel.Pokemon
import com.example.pokemon.data.domainmodel.Stat
import com.example.pokemon.data.domainmodel.StatInfo
import com.example.pokemon.data.domainmodel.Type
import com.example.pokemon.ui.screen.pokemondetail.PokemonDetailScreen
import com.example.pokemon.ui.screen.pokemondetail.PokemonDetailState
import org.junit.Rule
import org.junit.Test

class PokemonDetailsInstrumentationTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun checkDisplayedElements() {
        val pokemon = Pokemon(
            id = -1,
            name = "Charmender",
            types = listOf(Type.DRAGON, Type.FIRE, Type.ELECTRIC, Type.STEEL),
            stats = listOf(StatInfo(Stat.HP, 123), StatInfo(Stat.SPEED, 234))
        )
        val state = PokemonDetailState(pokemon = pokemon)

        composeTestRule.setContent {
            PokemonDetailScreen(state = state, disableImageFetching = true) {}
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Charmender").assertIsDisplayed()
        composeTestRule.onNodeWithText("dragon").assertIsDisplayed()
        composeTestRule.onNodeWithText("fire").assertIsDisplayed()
        composeTestRule.onNodeWithText("electric").assertIsDisplayed()
        composeTestRule.onNodeWithText("steel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Base Stat").assertIsDisplayed()
        composeTestRule.onNodeWithText("HP").assertIsDisplayed()
        composeTestRule.onNodeWithText("SPEED").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Unknown pokemon image")
            .assertIsDisplayed()
    }
}