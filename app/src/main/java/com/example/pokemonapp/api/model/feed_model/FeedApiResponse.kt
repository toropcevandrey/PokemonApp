package com.example.pokemonapp.api.model.feed_model

data class FeedApiResponse(
    val count: Int,
    val data: List<Data>,
    val page: Int,
    val pageSize: Int,
    val totalCount: Int
)