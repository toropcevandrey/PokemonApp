package com.example.pokemonapp.features.favorite.domain

import com.example.pokemonapp.features.favorite.data.FavoriteData

interface FavoriteRepository {

    suspend fun getFavoriteCards(): List<FavoriteData>

    suspend fun removeFromFavorite(id: String)
}