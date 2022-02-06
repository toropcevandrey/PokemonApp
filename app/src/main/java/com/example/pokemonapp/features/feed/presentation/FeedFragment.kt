package com.example.pokemonapp.features.feed.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.App
import com.example.pokemonapp.R
import com.example.pokemonapp.features.details.presentation.DetailsActivity
import com.example.pokemonapp.utils.Constants.ID
import com.example.pokemonapp.utils.Constants.SPAN_COUNT
import javax.inject.Inject

class FeedFragment : Fragment(), FeedListAdapter.OnPokemonClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: FeedViewModel? = null
    private lateinit var rvFeed: RecyclerView
    private lateinit var adapterFeed: FeedListAdapter
    private lateinit var tvError: TextView
    private lateinit var pgLoading: ProgressBar
    private lateinit var btnRefresh: Button
    private lateinit var etSearch: EditText

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
        viewModel = ViewModelProvider(this, factory).get(FeedViewModel::class.java)
        initViews(view)
        setObservers()
        rvAddOnScrollListener()
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel?.updateList()
    }

    override fun onPokemonClick(id: String) {
        openDetails(id)
    }

    override fun onFavoriteClick(id: String) {
        viewModel?.onFavoriteClicked(id)
    }

    private fun initViews(view: View) {
        rvFeed = view.findViewById(R.id.rv_feed)
        btnRefresh = view.findViewById(R.id.btn_feed_refresh)
        btnRefresh.setOnClickListener {
            etSearch.editableText.clear()
            viewModel?.loadFeed()
        }
        etSearch = view.findViewById(R.id.et_search)
        tvError = view.findViewById(R.id.tv_feed_error)
        pgLoading = view.findViewById(R.id.pb_feed_loading)
        adapterFeed = FeedListAdapter(this)
        rvFeed.adapter = adapterFeed
        rvFeed.layoutManager = GridLayoutManager(view.context, SPAN_COUNT)

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel?.onSearchEntered(p0.toString())
            }
        })
    }

    private fun setObservers() {
        viewModel?.feedLiveData?.observe(viewLifecycleOwner) { state ->
            val isError = state is FeedState.Error
            val isSuccess = state is FeedState.Success
            val isLoading = state is FeedState.Loading
            rvFeed.isVisible = isSuccess
            btnRefresh.isVisible = isError
            tvError.isVisible = isError
            pgLoading.isVisible = isLoading

            if (isSuccess) {
                adapterFeed.submitList((state as FeedState.Success).feed)
            }
        }
    }

    private fun openDetails(id: String) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
    }

    private fun rvAddOnScrollListener() {
        rvFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isLoaded = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoaded
                ) {
                    isLoaded = true
                    viewModel?.loadNextPage()
                }
                isLoaded = false
            }
        })
    }
}