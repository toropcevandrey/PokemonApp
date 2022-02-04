package com.example.pokemonapp.features.details.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemonapp.App
import com.example.pokemonapp.R
import com.example.pokemonapp.utils.Constants.ID
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: DetailsViewModel? = null
    private var id: String? = null
    private lateinit var groupDetails: Group
    private lateinit var ivDetailAvatar: ImageView
    private lateinit var tvDetailName: TextView
    private lateinit var tvDetailRarity: TextView
    private lateinit var tvDetailType: TextView
    private lateinit var tvDetailSubtype: TextView
    private lateinit var tvDetailHealth: TextView
    private lateinit var tvDetailAttackType: TextView
    private lateinit var fab: FloatingActionButton
    private lateinit var tvError: TextView
    private lateinit var btnRefresh: Button
    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        App.getComponent().inject(this)
        takeIntent()
        initViews()
        setupViewModel()
        setObservers()
        id?.let { viewModel?.init(id!!) }
    }

    private fun initViews() {
        ivDetailAvatar = findViewById(R.id.iv_detail_avatar)
        tvDetailName = findViewById(R.id.tv_detail_name)
        tvDetailRarity = findViewById(R.id.tv_detail_rarity)
        tvDetailType = findViewById(R.id.tv_detail_type)
        tvDetailSubtype = findViewById(R.id.tv_detail_subtype)
        tvDetailHealth = findViewById(R.id.tv_detail_health)
        groupDetails = findViewById(R.id.group_details)
        tvError = findViewById(R.id.tv_detail_error)
        btnRefresh = findViewById(R.id.btn_detail_refresh)
        btnRefresh.setOnClickListener {
            viewModel?.init(id!!)
        }
        pbLoading = findViewById(R.id.pb_detail_loading)
        fab = findViewById(R.id.fab_favorite)
        fab.setOnClickListener {
            viewModel?.onFavorite(id!!)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
    }

    private fun takeIntent() {
        id = intent.getStringExtra(ID).toString()
    }

    private fun setObservers() {
        viewModel?.init(id!!)
        viewModel?.detailsLiveData?.observe(this, { state ->
            val isError = state is DetailsState.Error
            val isLoading = state is DetailsState.Loading
            val isSuccess = state is DetailsState.Success
            btnRefresh.isVisible = isError
            tvError.isVisible = isError
            pbLoading.isVisible = isLoading
            groupDetails.isVisible = isSuccess
            fab.isVisible = isSuccess


            if (isSuccess) {
                Glide.with(this)
                    .load((state as DetailsState.Success).detail.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(ivDetailAvatar)

                tvDetailName.text = state.detail.name
                tvDetailRarity.text = state.detail.rarity
                tvDetailType.text = state.detail.type
                tvDetailSubtype.text = state.detail.subtype
                tvDetailHealth.text = state.detail.health
                setIconOnFab(state.detail.favorite)
            }
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