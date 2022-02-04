package com.example.pokemonapp.features.feed.data

import com.example.pokemonapp.api.ApiService
import com.example.pokemonapp.api.model.feed_model.FeedApiResponse
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.data.FavoriteDataDao
import com.example.pokemonapp.features.feed.domain.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val favoriteDataDao: FavoriteDataDao
) : FeedRepository {

    override suspend fun getFeedApiResponse(): FeedApiResponse {
        return apiService.getCards()
    }

    override suspend fun getFavoriteCards(): List<FavoriteData> {
        return favoriteDataDao.getAll()
    }

    override suspend fun addToFavorite(favoriteData: FavoriteData) {
        favoriteDataDao.addToFavorite(favoriteData)
    }

    override suspend fun removeFromFavorite(id: String) {
        favoriteDataDao.removeFromFavorite(id)
    }
}