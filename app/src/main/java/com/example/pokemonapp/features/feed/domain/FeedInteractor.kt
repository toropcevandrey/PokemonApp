package com.example.pokemonapp.features.feed.domain

import com.example.pokemonapp.api.model.feed_model.FeedApiResponse
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.domain.FavoriteModel
import javax.inject.Inject

class FeedInteractor @Inject constructor(private val feedRepository: FeedRepository) {

    suspend fun getFeedModelCards(page: Int): List<FeedModel> {
        val feedApiResponse: FeedApiResponse = feedRepository.getFeedApiResponse(page)
        return generateFeedModelList(feedApiResponse)
    }

    private fun generateFeedModelList(feedApiResponse: FeedApiResponse): List<FeedModel> {
        val list = mutableListOf<FeedModel>()
        feedApiResponse.data.forEach { element ->
            list.add(
                FeedModel(
                    element.id,
                    element.name,
                    element.images.large
                )
            )
        }
        return list
    }

    suspend fun searchCard(query: String, page: Int): List<SearchModel> {
        val feedApiResponse: FeedApiResponse = feedRepository.searchCard(query, page)
        return generateSearhFeedModelList(feedApiResponse)
    }

    private fun generateSearhFeedModelList(feedApiResponse: FeedApiResponse): List<SearchModel> {
        val list = mutableListOf<SearchModel>()
        feedApiResponse.data.forEach { element ->
            list.add(
                SearchModel(
                    element.id,
                    element.name,
                    element.images.large
                )
            )
        }
        return list
    }


    suspend fun getFavoriteModelCards(): List<FavoriteModel> {
        val favoriteData: List<FavoriteData> = feedRepository.getFavoriteCards()
        return generateFavoriteModelList(favoriteData)

    }

    private fun generateFavoriteModelList(favoriteData: List<FavoriteData>): List<FavoriteModel> {
        val list = mutableListOf<FavoriteModel>()
        favoriteData.forEach { element ->
            list.add(FavoriteModel(element.id, element.name, element.image))
        }
        return list
    }

    suspend fun addToFavorite(favoriteData: FavoriteData) {
        feedRepository.addToFavorite(favoriteData)
    }

    suspend fun removeFromFavorite(id: String) {
        feedRepository.removeFromFavorite(id)
    }


}