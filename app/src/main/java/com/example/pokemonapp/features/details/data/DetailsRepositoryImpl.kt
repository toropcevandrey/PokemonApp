package com.example.pokemonapp.features.details.data

import com.example.pokemonapp.api.ApiService
import com.example.pokemonapp.api.model.details_model.DetailsApiResponse
import com.example.pokemonapp.features.details.domain.DetailsRepository
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.data.FavoriteDataDao
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val favoriteDataDao: FavoriteDataDao
) : DetailsRepository {

    override suspend fun getDetailsFromApi(id: String): DetailsApiResponse {
        return apiService.getDetails(id)
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