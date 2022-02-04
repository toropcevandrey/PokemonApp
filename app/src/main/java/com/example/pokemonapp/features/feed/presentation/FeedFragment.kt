package com.example.pokemonapp.features.feed.presentation

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

class FeedFragment : Fragment(), FeedListAdapter.OnPokemonClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: FeedViewModel? = null
    private lateinit var rvFeed: RecyclerView
    private lateinit var adapterFeed: FeedListAdapter
    private val bundle: Bundle = Bundle()
    private val fragment: DetailsFragment = DetailsFragment()

    companion object {
        const val TAG = "FRAGMENT_FEED"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_feed, container, false)
        App.getComponent().inject(this)
        initViews(view)
        setupFeedViewModel()
        setObservers()
        return view
    }

    override fun onPokemonClick(id: String) {
        openDetailsFragment(id)
    }

    override fun onFavoriteClick(id: String) {
        viewModel?.onFavorite(id)
    }

    private fun setupFeedViewModel() {
        viewModel = ViewModelProvider(this, factory).get(FeedViewModel::class.java)
        viewModel?.updateList()
    }

    private fun initViews(view: View): View {
        rvFeed = view.findViewById(R.id.rv_feed)
        adapterFeed = FeedListAdapter(this)
        rvFeed.adapter = adapterFeed
        rvFeed.layoutManager = GridLayoutManager(view.context, SPAN_COUNT)
        return view
    }

    private fun setObservers() {
        viewModel?.feedLiveData?.observe(viewLifecycleOwner) { list ->
            adapterFeed.submitList(list)
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