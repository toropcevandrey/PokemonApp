package com.example.pokemonapp.api

import com.example.pokemonapp.api.model.details_model.DetailsApiResponse
import com.example.pokemonapp.api.model.feed_model.FeedApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("cards")
    suspend fun getCards(
        @Query("pageSize") pageSize: Int = 10,
        @Query("X-Api-Key") token: String = "3ac80715-ca5b-4d50-9037-1af7ccb25d11"
    ): FeedApiResponse

    @GET("cards/{id}")
    suspend fun getDetails(
        @Path("id") id: String,
        @Query("X-Api-Key") token: String = "3ac80715-ca5b-4d50-9037-1af7ccb25d11"
    ): DetailsApiResponse
}