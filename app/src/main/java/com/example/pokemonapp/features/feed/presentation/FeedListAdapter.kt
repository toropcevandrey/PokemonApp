package com.example.pokemonapp.features.feed.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pokemonapp.features.feed.domain.FeedModel

class FeedListAdapter(private val onClickListener: OnPokemonClickListener) :
    ListAdapter<FeedViewData, FeedViewHolder>(FeedComparator()) {

    interface OnPokemonClickListener {
        fun onPokemonClick(id: String)
        fun onFavoriteClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder.create(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.image, current.name, current.favorite)

    }
}