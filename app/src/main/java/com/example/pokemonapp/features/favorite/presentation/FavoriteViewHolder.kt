package com.example.pokemonapp.features.favorite.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemonapp.R

class FavoriteViewHolder(
    itemView: View,
    private val onPokemonClickListener: FavoriteListAdapter.OnPokemonClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val iv: ImageView = itemView.findViewById(R.id.iv_avatar)
    private val tv: TextView = itemView.findViewById(R.id.tv_name)
    private val btn: Button = itemView.findViewById(R.id.btn_favorite)

    fun bind(id: String, image: String, name: String) {
        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_placeholder)
            .into(iv)

        iv.setOnClickListener {
            onPokemonClickListener.onPokemonClick(id)
        }

        btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_like_active)

        btn.setOnClickListener {
            onPokemonClickListener.onFavoriteClick(id)
        }

        tv.text = name
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onPokemonClickListener: FavoriteListAdapter.OnPokemonClickListener
        ): FavoriteViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_feed, parent, false)
            return FavoriteViewHolder(view, onPokemonClickListener)
        }
    }
}