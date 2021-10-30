package com.example.pokemon.di

import com.example.pokemon.data.PokemonAPI
import com.example.pokemon.data.PokemonRepository
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
    fun providesPokemonRepository(apiService: PokemonAPI) = PokemonRepository(apiService)

}