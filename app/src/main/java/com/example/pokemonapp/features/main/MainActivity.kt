package com.example.pokemonapp.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import com.example.pokemonapp.R
import com.example.pokemonapp.features.favorite.presentation.FavoriteFragment
import com.example.pokemonapp.features.feed.presentation.FeedFragment
import com.example.pokemonapp.utils.attach
import com.example.pokemonapp.utils.detach
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)
        firstInit()
        configureBottomNavigation()
    }

    private fun changeFragment(tag: String) {
        val item = supportFragmentManager.findFragmentByTag(tag) ?: createFragmentByTag(tag)
        if (item.isAdded) return
        supportFragmentManager.detach(R.id.fl_root_container)
        supportFragmentManager.attach(item, tag, R.id.fl_root_container)
        supportFragmentManager.executePendingTransactions()
    }

    private fun createFragmentByTag(tag: String): Fragment =
        when (tag) {
            FeedFragment.TAG -> {
                FeedFragment()
            }
            else -> {
                FavoriteFragment()
            }
        }

    private fun configureBottomNavigation() {
        val bottomNavigationView =
            findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_feed -> changeFragment(FeedFragment.TAG)
                R.id.action_favorite -> changeFragment(FavoriteFragment.TAG)
            }
            true
        }
    }

    private fun firstInit() {
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_root_container, FeedFragment())
                .commit()
        }
    }
}