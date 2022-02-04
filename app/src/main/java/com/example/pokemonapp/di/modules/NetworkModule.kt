package com.example.pokemonapp.di.modules

import com.example.pokemonapp.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder() = RetrofitBuilder.apiService
}