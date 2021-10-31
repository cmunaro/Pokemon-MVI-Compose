package com.example.pokemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemon.data.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemote(list: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE pokemonId = :id")
    fun getRemoteKeys(id: Int): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()

}