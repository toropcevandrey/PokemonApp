package com.example.pokemonapp.features.favorite.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDataDao {
    @Query("SELECT * FROM favorite_table")
    fun getAll() : List<FavoriteData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(favoriteData: FavoriteData)

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun removeFromFavorite(id: String)

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    suspend fun getDetailsById(id: String): FavoriteData
}