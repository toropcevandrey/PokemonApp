package com.example.pokemonapp.di.modules

import com.example.pokemonapp.api.ApiService
import com.example.pokemonapp.features.favorite.data.FavoriteDataDao
import com.example.pokemonapp.features.feed.data.FeedRepositoryImpl
import com.example.pokemonapp.features.feed.domain.FeedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FeedModule {
    @Provides
    @Singleton
    fun provideFeedRepository(
        apiService: ApiService,
        favoriteDataDao: FavoriteDataDao
    ): FeedRepository =
        FeedRepositoryImpl(apiService, favoriteDataDao)
}