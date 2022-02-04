package com.example.pokemonapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.features.favorite.data.FavoriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideRoomDatabase() =
        Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite_table")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db: FavoriteDatabase) = db.favoriteDataDao()
}