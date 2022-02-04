package com.example.pokemonapp.features.favorite.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pokemonapp.features.favorite.domain.FavoriteModel

class FavoriteListAdapter(private val onClickListener: OnPokemonClickListener) :
    ListAdapter<FavoriteViewData, FavoriteViewHolder>(FavoriteComparator()) {

    interface OnPokemonClickListener {
        fun onPokemonClick(id: String)
        fun onFavoriteClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.create(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.image, current.name)
    }
}