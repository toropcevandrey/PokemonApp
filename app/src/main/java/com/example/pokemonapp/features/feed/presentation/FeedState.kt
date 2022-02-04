package com.example.pokemonapp.features.feed.presentation

sealed class FeedState {
    class Success(val feed: List<FeedViewData>) : FeedState()
    object Error : FeedState()
    object Loading : FeedState()
}
