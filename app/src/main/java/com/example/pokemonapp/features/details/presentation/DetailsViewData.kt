package com.example.pokemonapp.features.details.presentation

data class DetailsViewData(
    var name: String?,
    val image: String?,
    val type: String?,
    val subtype: String?,
    val health: String?,
    val rarity: String?,
    var attack1: String?,
    val attack2: String?,
    val favorite: Boolean
)