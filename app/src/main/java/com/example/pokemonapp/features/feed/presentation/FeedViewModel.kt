package com.example.pokemonapp.features.feed.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.domain.FavoriteModel
import com.example.pokemonapp.features.feed.domain.FeedInteractor
import com.example.pokemonapp.features.feed.domain.FeedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedInteractor: FeedInteractor
) : ViewModel() {

    val feedLiveData: MutableLiveData<FeedState> = MutableLiveData(FeedState.Loading)
    private var feedList: MutableList<FeedModel> = mutableListOf()
    private var favoriteList: MutableList<FavoriteModel> = mutableListOf()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                feedList.addAll(feedInteractor.getFeedModelCards())
                favoriteList.addAll(feedInteractor.getFavoriteModelCards())
                feedLiveData.postValue(FeedState.Loading)
                feedLiveData.postValue(
                    FeedState.Success(
                        generateFeedViewData(
                            feedList,
                            favoriteList
                        )
                    )
                )
            } catch (e: Exception) {
                feedLiveData.postValue(FeedState.Error)
            }
        }
    }

    fun onFavorite(id: String) {
        viewModelScope.launch {
            val favoriteItem = favoriteList.find { element ->
                id == element.id
            }
            if (favoriteItem == null) {
                addToFavorite(id)
            } else {
                removeFromFavorite(id)
            }
            updateList()
        }
    }

    private fun generateFeedViewData(
        feedModel: MutableList<FeedModel>,
        favoriteModel: MutableList<FavoriteModel>
    ): List<FeedViewData> {
        val list: MutableList<FeedViewData> = mutableListOf()
        val favoriteListId = favoriteModel.map { it.id }
        feedModel.forEach { element ->
            val isFavorite = favoriteListId.contains(element.id)
            list.add(
                FeedViewData(
                    id = element.id,
                    name = element.name,
                    image = element.image,
                    favorite = isFavorite
                )
            )
        }
        return list
    }

    fun updateList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteList.clear()
                feedLiveData.postValue(FeedState.Loading)
                favoriteList.addAll(feedInteractor.getFavoriteModelCards())
                val list = generateFeedViewData(feedList, favoriteList)
                feedLiveData.postValue(FeedState.Success(list))
            } catch (e: Exception) {
                feedLiveData.postValue(FeedState.Error)
            }
        }
    }

    private suspend fun addToFavorite(id: String) {
        val data: FeedModel = feedList.find { element ->
            id == element.id
        } ?: return
        feedInteractor.addToFavorite(
            FavoriteData(
                id = data.id,
                name = data.name,
                image = data.image
            )
        )
    }

    private suspend fun removeFromFavorite(id: String) {
        feedInteractor.removeFromFavorite(id)
    }
}