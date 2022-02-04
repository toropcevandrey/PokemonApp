package com.example.pokemonapp.features.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.App
import com.example.pokemonapp.R
import com.example.pokemonapp.features.details.presentation.DetailsFragment
import com.example.pokemonapp.utils.Constants.SPAN_COUNT
import com.example.pokemonapp.utils.attach
import com.example.pokemonapp.utils.detach
import javax.inject.Inject

class FavoriteFragment : Fragment(), FavoriteListAdapter.OnPokemonClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: FavoriteViewModel? = null
    private lateinit var rvFavorite: RecyclerView
    private lateinit var adapterFavorite: FavoriteListAdapter
    private val bundle: Bundle = Bundle()
    private val fragment: DetailsFragment = DetailsFragment()

    companion object {
        const val TAG = "FEED_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite, container, false)
        App.getComponent().inject(this)
        initViews(view)
        setupFavoriteViewModel()
        setObservers()
        return view
    }

    override fun onPokemonClick(id: String) {
        openDetailsFragment(id)
    }

    override fun onFavoriteClick(id: String) {
        viewModel?.onFavoriteClick(id)
    }

    private fun setupFavoriteViewModel() {
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        viewModel?.updateList()
    }

    private fun initViews(view: View): View {
        rvFavorite = view.findViewById(R.id.rv_favorite)
        adapterFavorite = FavoriteListAdapter(this)
        rvFavorite.adapter = adapterFavorite
        rvFavorite.layoutManager = GridLayoutManager(view.context, SPAN_COUNT)
        return view
    }

    private fun setObservers() {
        viewModel?.favoriteLiveData?.observe(viewLifecycleOwner) { list ->
            adapterFavorite.submitList(list)
        }
    }

    private fun openDetailsFragment(id: String) {
        bundle.putString("id", id)
        fragment.arguments = bundle
        parentFragmentManager.detach(R.id.fl_root_container)
        parentFragmentManager.attach(fragment, DetailsFragment.TAG, R.id.fl_root_container)
        parentFragmentManager.executePendingTransactions()
    }
}