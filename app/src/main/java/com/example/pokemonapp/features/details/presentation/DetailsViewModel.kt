package com.example.pokemonapp.features.details.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.features.details.domain.DetailsInteractor
import com.example.pokemonapp.features.details.domain.DetailsModel
import com.example.pokemonapp.features.favorite.data.FavoriteData
import com.example.pokemonapp.features.favorite.domain.FavoriteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    val detailsLiveData: MutableLiveData<DetailsViewData> = MutableLiveData()
    private var favoriteList: MutableList<FavoriteModel> = mutableListOf()
    private lateinit var detailsModel: DetailsModel

    fun init(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                detailsModel = detailsInteractor.getDetailsInfoFromApi(id)
                favoriteList.addAll(detailsInteractor.getFavoriteModelCards())
                detailsLiveData.postValue(generateDetailsViewData(favoriteList, detailsModel))
            } catch (e: Exception) {

            }
        }
    }


    private fun generateDetailsViewData(
        favoriteList: List<FavoriteModel>,
        detailsModel: DetailsModel
    ): DetailsViewData {
        val favoriteListId = favoriteList.map { it.id }
        val isFavorite = favoriteListId.contains(detailsModel.id)
        return DetailsViewData(
            name = detailsModel.name,
            image = detailsModel.image,
            type = detailsModel.type,
            subtype = detailsModel.subtype,
            health = detailsModel.health,
            rarity = detailsModel.rarity,
            attack1 = detailsModel.attack1,
            attack2 = detailsModel.attack2,
            favorite = isFavorite
        )
    }

    fun onFavorite(id: String) {
        viewModelScope.launch {
            val isFavorite: Boolean
            val favoriteItem = favoriteList.find { element ->
                id == element.id
            }
            if (favoriteItem == null) {
                addToFavorite()
                isFavorite = true
            } else {
                removeFromFavorite(id)
                isFavorite = false
            }
            detailsLiveData.value = DetailsViewData(
                name = detailsModel.name,
                image = detailsModel.image,
                type = detailsModel.type,
                subtype = detailsModel.subtype,
                health = detailsModel.health,
                rarity = detailsModel.rarity,
                attack1 = detailsModel.attack1,
                attack2 = detailsModel.attack2,
                favorite = isFavorite
            )
        }
    }

    private suspend fun addToFavorite() {
        detailsInteractor.addToFavorite(
            FavoriteData(
                id = detailsModel.id,
                name = detailsModel.name!!,
                image = detailsModel.image!!
            )
        )
    }

    private suspend fun removeFromFavorite(id: String) {
        detailsInteractor.removeFromFavorite(id)
    }

}
