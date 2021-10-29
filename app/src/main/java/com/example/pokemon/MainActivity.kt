package com.example.pokemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("XENA", "ACTIVITY $lifecycle $lifecycleScope")
        setContent {
            val navController = rememberNavController()
            PokemonTheme {
                Log.d("XENA", "CONTENT $lifecycle $lifecycleScope")
                Surface(color = MaterialTheme.colors.background) {
                    PokemonNavController(navController = navController)
                }
            }
        }
    }
}