package com.example.pokemonapp.di.modules

import com.example.pokemonapp.api.ApiService
import com.example.pokemonapp.features.details.data.DetailsRepositoryImpl
import com.example.pokemonapp.features.details.domain.DetailsRepository
import com.example.pokemonapp.features.favorite.data.FavoriteDataDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailsModule {
    @Provides
    @Singleton
    fun provideDetailsRepository(
        apiService: ApiService,
        favoriteDataDao: FavoriteDataDao
    ): DetailsRepository =
        DetailsRepositoryImpl(apiService, favoriteDataDao)
}