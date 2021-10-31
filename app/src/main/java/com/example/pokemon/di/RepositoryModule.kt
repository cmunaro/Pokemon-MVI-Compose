package com.example.pokemon.di

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.local.PokemonDatabase
import com.example.pokemon.data.remote.PokemonAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesPokemonRepository(apiService: PokemonAPI, pokemonDatabase: PokemonDatabase) =
        PokemonRepository(apiService, pokemonDatabase)

}