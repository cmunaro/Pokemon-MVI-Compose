package com.example.pokemon.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemon.data.domainmodel.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Pokemon>)

    @Query("SELECT * FROM pokemon")
    fun pagingSource(): PagingSource<Int, Pokemon>

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()

}