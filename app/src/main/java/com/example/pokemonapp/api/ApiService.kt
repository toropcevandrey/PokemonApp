package com.example.pokemonapp.api

import com.example.pokemonapp.api.model.details_model.DetailsApiResponse
import com.example.pokemonapp.api.model.feed_model.FeedApiResponse
import com.example.pokemonapp.utils.Constants.TOKEN
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("cards")
    suspend fun getCards(
        @Query("pageSize") pageSize: Int = 10,
        @Query("X-Api-Key") token: String = TOKEN
    ): FeedApiResponse

    @GET("cards/{id}")
    suspend fun getDetails(
        @Path("id") id: String,
        @Query("X-Api-Key") token: String = TOKEN
    ): DetailsApiResponse
}