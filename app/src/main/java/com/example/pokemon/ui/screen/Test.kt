package com.example.pokemon.ui.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun Test(navController: NavHostController) {
    LocalLifecycleOwner.current.also { lifecycleOwner ->
        LaunchedEffect(true) {
            Log.d("XENA", "SCREEN 2 $lifecycleOwner ${lifecycleOwner.lifecycle} ${lifecycleOwner.lifecycleScope}")
            delay(2000)
        }
    }
}