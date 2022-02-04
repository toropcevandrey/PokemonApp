package com.example.pokemonapp.features.favorite.domain

import com.example.pokemonapp.features.favorite.data.FavoriteData
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun getFavoriteModelCards(): List<FavoriteModel> {
        val favoriteData: List<FavoriteData> = favoriteRepository.getFavoriteCards()
        return generateFavoriteModelList(favoriteData)

    }

    private fun generateFavoriteModelList(favoriteData: List<FavoriteData>): List<FavoriteModel> {
        val list = mutableListOf<FavoriteModel>()
        favoriteData.forEach { element ->
            list.add(FavoriteModel(element.id, element.name, element.image))
        }
        return list
    }

    suspend fun removeFromFavorite(id: String){
        favoriteRepository.removeFromFavorite(id)
    }
}