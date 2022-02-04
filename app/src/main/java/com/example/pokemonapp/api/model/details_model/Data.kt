package com.example.pokemonapp.api.model.details_model

data class Data(
    val attacks: List<Attack>?,
    val hp: String?,
    val images: Images,
    val name: String,
    val rarity: String?,
    val subtypes: List<String>?,
    val types: List<String>?,
    val id: String
)