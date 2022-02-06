package com.example.pokemonapp.features.feed.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.domain.FavoriteModel
import com.example.pokemonapp.features.feed.domain.FeedInteractor
import com.example.pokemonapp.features.feed.domain.FeedModel
import com.example.pokemonapp.features.feed.domain.SearchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedInteractor: FeedInteractor
) : ViewModel() {

    val feedLiveData: MutableLiveData<FeedState> = MutableLiveData(FeedState.Loading)
    private var feedList: MutableList<FeedModel> = mutableListOf()
    private var favoriteList: MutableList<FavoriteModel> = mutableListOf()
    private var searchList: MutableList<SearchModel> = mutableListOf()
    private var pageFeed: Int = 1
    private var pageSearch: Int = 1
    private var isSearch: Boolean = true
    private var query: String? = ""
    private var searchJob: Job? = null

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                feedList.addAll(feedInteractor.getFeedModelCards(pageFeed))
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

    private fun generateSearchFeedViewData(
        searchModel: MutableList<SearchModel>,
        favoriteModel: MutableList<FavoriteModel>
    ): List<FeedViewData> {
        val list: MutableList<FeedViewData> = mutableListOf()
        val favoriteListId = favoriteModel.map { it.id }
        searchModel.forEach { element ->
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
                if (isSearch) {
                    val list = generateSearchFeedViewData(searchList, favoriteList)
                    feedLiveData.postValue(FeedState.Success(list))
                } else {
                    val list = generateFeedViewData(feedList, favoriteList)
                    feedLiveData.postValue(FeedState.Success(list))
                }
            } catch (e: Exception) {
                feedLiveData.postValue(FeedState.Error)
            }
        }
    }

    private suspend fun addToFavorite(id: String) {
        if (isSearch) {
            val data: SearchModel = searchList.find { element ->
                id == element.id
            } ?: return
            feedInteractor.addToFavorite(
                FavoriteData(
                    id = data.id,
                    name = data.name,
                    image = data.image
                )
            )
        } else {
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

    }

    private suspend fun removeFromFavorite(id: String) {
        feedInteractor.removeFromFavorite(id)
    }


    fun loadNextPage() {
        if (!isSearch) {
            pageFeed++
            viewModelScope.launch {
                try {
                    feedList.addAll(feedInteractor.getFeedModelCards(pageFeed))
                    feedLiveData.value = (
                            FeedState.Success(
                                generateFeedViewData(
                                    feedList,
                                    favoriteList
                                )
                            )
                            )
                } catch (e: Exception) {
                    FeedState.Error
                }
            }
        } else {
            pageSearch++
            try {
                viewModelScope.launch {
                    searchList.addAll(feedInteractor.searchCard("name:$query*", pageSearch))
                    feedLiveData.value =
                        FeedState.Success(generateSearchFeedViewData(searchList, favoriteList))
                }
            } catch (e: java.lang.Exception) {
                FeedState.Error
            }
        }
    }

    fun onSearchEntered(query: String?) {
        if (this.query == query) return
        this.query = query
        pageSearch = 1
        searchList.clear()
        searchJob?.cancel()
        if (!query.isNullOrEmpty()) {
            isSearch = true
            try {
                searchJob = viewModelScope.launch {
                    feedLiveData.value = FeedState.Loading
                    delay(250)
                    searchList.addAll(feedInteractor.searchCard("name:$query*", 1))
                    feedLiveData.value =
                        FeedState.Success(generateSearchFeedViewData(searchList, favoriteList))
                }
            } catch (e: java.lang.Exception) {
                FeedState.Error
            }
        } else {
            isSearch = false
            feedLiveData.value = (
                    FeedState.Success(
                        generateFeedViewData(
                            feedList,
                            favoriteList
                        )
                    )
                    )
        }
    }
}