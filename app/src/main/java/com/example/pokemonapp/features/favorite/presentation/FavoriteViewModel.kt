package com.example.pokemonapp.features.favorite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.features.favorite.domain.FavoriteInteractor
import com.example.pokemonapp.features.favorite.domain.FavoriteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val favoriteInteractor: FavoriteInteractor) :
    ViewModel() {

    val favoriteLiveData: MutableLiveData<List<FavoriteViewData>> = MutableLiveData()
    private var favoriteList: MutableList<FavoriteModel> = mutableListOf()

    fun updateList() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteList.clear()
            favoriteList.addAll(favoriteInteractor.getFavoriteModelCards())
            favoriteLiveData.postValue(setViewData(favoriteList))
        }
    }

    private fun setViewData(favoriteModel: MutableList<FavoriteModel>): List<FavoriteViewData> {
        val list: MutableList<FavoriteViewData> = mutableListOf()
        favoriteModel.forEach { element ->
            list.add(FavoriteViewData(id = element.id, name = element.name, image = element.image))
        }
        return list
    }

    fun onFavoriteClick(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.removeFromFavorite(id)
            updateList()
        }
    }
}