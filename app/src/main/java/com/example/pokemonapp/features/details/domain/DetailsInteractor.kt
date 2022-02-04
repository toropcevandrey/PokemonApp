package com.example.pokemonapp.features.details.domain

import com.example.pokemonapp.api.model.details_model.DetailsApiResponse
import com.example.pokemonapp.features.details.data.DetailsRepositoryImpl
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.domain.FavoriteModel
import javax.inject.Inject

class DetailsInteractor @Inject constructor(private val detailsRepository: DetailsRepository) {

    suspend fun getDetailsInfoFromApi(id: String): DetailsModel {
        val detailsApiResponse: DetailsApiResponse = detailsRepository.getDetailsFromApi(id)
        return generateDetailsModelFromApi(detailsApiResponse)
    }

    suspend fun getFavoriteModelCards(): List<FavoriteModel> {
        val favoriteData: List<FavoriteData> = detailsRepository.getFavoriteCards()
        return generateFavoriteModelList(favoriteData)

    }

    private fun generateFavoriteModelList(favoriteData: List<FavoriteData>): List<FavoriteModel> {
        val list = mutableListOf<FavoriteModel>()
        favoriteData.forEach { element ->
            list.add(FavoriteModel(element.id, element.name, element.image))
        }

        return list
    }

    private fun generateDetailsModelFromApi(detailsApiResponse: DetailsApiResponse): DetailsModel {
        return DetailsModel(
            id = detailsApiResponse.data.id,
            name = detailsApiResponse.data.name,
            image = detailsApiResponse.data.images.large,
            type = detailsApiResponse.data.types?.get(0) ?: "",
            health = detailsApiResponse.data.hp ?: "",
            rarity = detailsApiResponse.data.rarity ?: "",
            attack1 = detailsApiResponse.data.attacks?.get(0)?.name ?: "",
            attack2 = detailsApiResponse.data.attacks?.get(1)?.name ?: "",
            subtype = detailsApiResponse.data.subtypes?.get(0) ?: "",
        )
    }

    suspend fun addToFavorite(favoriteData: FavoriteData) {
        detailsRepository.addToFavorite(favoriteData)
    }

    suspend fun removeFromFavorite(id: String){
        detailsRepository.removeFromFavorite(id)
    }
}