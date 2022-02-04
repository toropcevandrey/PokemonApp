package com.example.pokemonapp.features.favorite.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteData::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteDataDao(): FavoriteDataDao
}