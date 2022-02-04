package com.example.pokemonapp.features.details.domain

import com.example.pokemonapp.api.ApiService
import com.example.pokemonapp.api.model.details_model.DetailsApiResponse
import com.example.pokemonapp.features.favorite.data.FavoriteData

interface DetailsRepository {
    suspend fun getDetailsFromApi(id: String): DetailsApiResponse

    suspend fun getFavoriteCards():List<FavoriteData>

    suspend fun addToFavorite(favoriteData: FavoriteData)

    suspend fun removeFromFavorite(id: String)
}