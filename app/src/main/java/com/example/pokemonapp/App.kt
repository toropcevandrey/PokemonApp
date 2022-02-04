package com.example.pokemonapp

import android.app.Application
import com.example.pokemonapp.di.components.AppComponent
import com.example.pokemonapp.di.components.DaggerAppComponent
import com.example.pokemonapp.di.modules.DbModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().dbModule(DbModule(applicationContext)).build()
    }

    companion object{
        private lateinit var component: AppComponent

        fun getComponent(): AppComponent {
            return component
        }
    }
}