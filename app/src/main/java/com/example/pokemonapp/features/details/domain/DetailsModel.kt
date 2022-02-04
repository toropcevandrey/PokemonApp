package com.example.pokemonapp.features.details.domain

data class DetailsModel(
    val id: String,
    val name: String?,
    val image: String?,
    val type: String?,
    val subtype: String?,
    val health: String?,
    val rarity: String?,
    val attack1: String?,
    val attack2: String?
)