package com.example.pokemonapp.features.details.presentation

sealed class DetailsState {
    class Success(val detail: DetailsViewData) : DetailsState()
    object Error : DetailsState()
    object Loading : DetailsState()
}
