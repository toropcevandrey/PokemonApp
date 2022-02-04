package com.example.pokemonapp.features.feed.presentation

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

class FeedViewHolder(
    itemView: View,
    private val onPokemonClickListener: FeedListAdapter.OnPokemonClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val iv: ImageView = itemView.findViewById(R.id.iv_avatar)
    private val tv: TextView = itemView.findViewById(R.id.tv_name)
    private val btn: Button = itemView.findViewById(R.id.btn_favorite)

    fun bind(id: String, image: String, name: String, favorite: Boolean) {
        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_placeholder)
            .into(iv)

        tv.text = name

        iv.setOnClickListener {
            onPokemonClickListener.onPokemonClick(id)
        }

        val icon = if (favorite) {
            R.drawable.ic_like_active
        } else {
            R.drawable.ic_like_unactive
        }

        btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, icon)

        btn.setOnClickListener {
            onPokemonClickListener.onFavoriteClick(id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onPokemonClickListener: FeedListAdapter.OnPokemonClickListener
        ): FeedViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_feed, parent, false)
            return FeedViewHolder(view, onPokemonClickListener)
        }
    }
}