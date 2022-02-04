package com.example.pokemonapp.di.modules

import com.example.pokemonapp.features.favorite.data.FavoriteDataDao
import com.example.pokemonapp.features.favorite.data.FavoriteRepositoryImpl
import com.example.pokemonapp.features.favorite.domain.FavoriteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoriteModule {
    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteDataDao: FavoriteDataDao): FavoriteRepository =
        FavoriteRepositoryImpl(favoriteDataDao)
}