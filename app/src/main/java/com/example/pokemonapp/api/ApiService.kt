package com.example.pokemonapp.api

import com.example.pokemonapp.api.model.details_model.DetailsApiResponse
import com.example.pokemonapp.api.model.feed_model.FeedApiResponse
import com.example.pokemonapp.utils.Constants.TOKEN
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("cards/?$TOKEN")
    suspend fun getCards(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10
    ): FeedApiResponse

    @GET("cards/{id}?$TOKEN")
    suspend fun getDetails(
        @Path("id") id: String
    ): DetailsApiResponse

    @GET("cards/?$TOKEN")
    suspend fun searchCards(
        @Query("q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10
    ): FeedApiResponse
}