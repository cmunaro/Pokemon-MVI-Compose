package com.example.pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.data.model.RemoteKey

@Database(entities = [Pokemon::class, RemoteKey::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}