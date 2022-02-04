package com.example.pokemonapp.api.model.feed_model

data class Attack(
    val convertedEnergyCost: Int?,
    val cost: List<String>?,
    val damage: String?,
    val name: String?,
    val text: String?
)