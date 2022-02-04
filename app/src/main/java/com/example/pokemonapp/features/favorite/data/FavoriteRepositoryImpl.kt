package com.example.pokemonapp.features.favorite.data

import com.example.pokemonapp.features.favorite.domain.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDataDao: FavoriteDataDao
    ) : FavoriteRepository {

    override suspend fun getFavoriteCards(): List<FavoriteData> {
        return favoriteDataDao.getAll()
    }

    override suspend fun removeFromFavorite(id: String) {
        favoriteDataDao.removeFromFavorite(id)
    }


}