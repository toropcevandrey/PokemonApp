package com.example.pokemonapp.features.favorite.presentation

import androidx.recyclerview.widget.DiffUtil

class FavoriteComparator : DiffUtil.ItemCallback<FavoriteViewData>() {
    override fun areItemsTheSame(oldItem: FavoriteViewData, newItem: FavoriteViewData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteViewData, newItem: FavoriteViewData): Boolean {
        return oldItem == newItem
    }
}