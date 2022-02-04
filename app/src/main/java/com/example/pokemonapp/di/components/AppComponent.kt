package com.example.pokemonapp.di.components

import com.example.pokemonapp.di.modules.*
import com.example.pokemonapp.features.details.presentation.DetailsActivity
import com.example.pokemonapp.features.favorite.presentation.FavoriteFragment
import com.example.pokemonapp.features.feed.presentation.FeedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, DetailsModule::class, DbModule::class, FeedModule::class, FavoriteModule::class])
interface AppComponent {
    fun inject(fragment: FeedFragment)
    fun inject(activity: DetailsActivity)
    fun inject(fragment: FavoriteFragment)
}