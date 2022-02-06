package com.example.pokemonapp.features.feed.domain

import com.example.pokemonapp.api.model.feed_model.FeedApiResponse
import com.example.pokemonapp.features.favorite.data.FavoriteData

interface FeedRepository {
    suspend fun getFeedApiResponse(page: Int): FeedApiResponse

    suspend fun getFavoriteCards(): List<FavoriteData>

    suspend fun addToFavorite(favoriteData: FavoriteData)

    suspend fun removeFromFavorite(id: String)

    suspend fun searchCard(query: String, page: Int): FeedApiResponse
}