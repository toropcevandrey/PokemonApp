package com.example.pokemonapp.features.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemonapp.App
import com.example.pokemonapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: DetailsViewModel? = null
    private var bundle: Bundle? = null
    private var id: String? = null
    private lateinit var ivDetailAvatar: ImageView
    private lateinit var tvDetailName: TextView
    private lateinit var tvDetailRarity: TextView
    private lateinit var tvDetailType: TextView
    private lateinit var tvDetailSubtype: TextView
    private lateinit var tvDetailHealth: TextView
    private lateinit var tvDetailAttackType: TextView
    private lateinit var fab: FloatingActionButton

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_detail, container, false)
        App.getComponent().inject(this)
        takeBundle()
        initViews(view)
        setupViewModel()
        setObservers()
        id?.let { viewModel?.init(id!!) }
        return view
    }

    private fun initViews(view: View) {
        ivDetailAvatar = view.findViewById(R.id.iv_detail_avatar)
        tvDetailName = view.findViewById(R.id.tv_detail_name)
        tvDetailRarity = view.findViewById(R.id.tv_detail_rarity)
        tvDetailType = view.findViewById(R.id.tv_detail_type)
        tvDetailSubtype = view.findViewById(R.id.tv_detail_subtype)
        tvDetailHealth = view.findViewById(R.id.tv_detail_health)
        fab = view.findViewById(R.id.fab_favorite)
        fab.setOnClickListener {
            viewModel?.onFavorite(id!!)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
    }

    private fun takeBundle() {
        bundle = this.arguments
        if (bundle != null)
            id = bundle?.getString("id")
    }

    private fun setObservers() {
        id?.let { viewModel?.init(id!!) }
        viewModel?.detailsLiveData?.observe(viewLifecycleOwner, { response ->
            Glide.with(this)
                .load(response.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivDetailAvatar)

            tvDetailName.text = response.name
            tvDetailRarity.text = response.rarity
            tvDetailType.text = response.type
            tvDetailSubtype.text = response.subtype
            tvDetailHealth.text = response.health
            setIconOnFab(response.favorite)
        })
    }

    private fun setIconOnFab(favorite: Boolean) {
        if (favorite) {
            fab.setImageResource(R.drawable.ic_like_fab_active)
        } else {
            fab.setImageResource(R.drawable.ic_like_fab_unactive)
        }
    }
}