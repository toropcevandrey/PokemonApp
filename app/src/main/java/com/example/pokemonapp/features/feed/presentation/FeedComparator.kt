package com.example.pokemonapp.features.feed.presentation

import androidx.recyclerview.widget.DiffUtil

class FeedComparator : DiffUtil.ItemCallback<FeedViewData>() {
    override fun areItemsTheSame(oldItem: FeedViewData, newItem: FeedViewData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedViewData, newItem: FeedViewData): Boolean {
        return oldItem == newItem
    }
}