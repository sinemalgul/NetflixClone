package com.example.netflixclone.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.netflixclone.common.gone
import com.example.netflixclone.common.setViewsGone
import com.example.netflixclone.common.setViewsVisible
import com.example.netflixclone.common.showSnackbar
import com.example.netflixclone.common.visible
import com.example.netflixclone.databinding.FragmentSearchBinding
import com.example.netflixclone.ui.home.HomeFragmentDirections
import com.example.netflixclone.ui.home.HomeViewModel
import com.example.netflixclone.ui.home.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val searchViewModel by viewModels<SearchViewModel>()

    private val searchAdapter by lazy {
        SearchAdapter(
            onSearchClick = {
                val actionSearch = SearchFragmentDirections.searchToMovies(it)
                findNavController().navigate(actionSearch)
            }
        ) }

    private val moviesAdapter by lazy {
        MoviesAdapter(
            onMoviesClick = {
                val actionMovies = SearchFragmentDirections.searchToMovies(it)
                findNavController().navigate(actionMovies)
            }
        ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies()
        initObserver()
        initSearchObserver()
        initSearch()

    }

    private fun initSearchObserver() = with(binding) {
        searchViewModel.searchResults.observe(viewLifecycleOwner) {state ->
            state.searchList?.let {
                rvResultSearch.adapter = searchAdapter
                searchAdapter.updateList(state.searchList)
            }
        }
    }

    private fun initSearch() = with(binding) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val lastQuery = query.orEmpty()
                searchViewModel.searchProducts(lastQuery)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if(newText.length >= 2) {
                        searchViewModel.searchProducts(newText)
                        setViewsVisible(rvResultSearch)
                        setViewsGone(tvTopSearch, rvTopSearch)

                    } else {
                        searchAdapter.clearList()
                        setViewsGone(rvResultSearch)
                        setViewsVisible(rvTopSearch, tvTopSearch)
                    }
                }
                return true
            }
        })
    }

    private fun initObserver() = with(binding) {
        viewModel.moviesState.observe(viewLifecycleOwner) { state ->
            state.moviesList?.let {
                val randomMovies = it.shuffled()
                rvTopSearch.setHasFixedSize(true)
                rvTopSearch.adapter = moviesAdapter
                moviesAdapter.updateList(randomMovies)
            }

            state.errorMessage?.let {
                requireView().showSnackbar("Empty List")
            }

            state.failMessage?.let {
                requireView().showSnackbar("Empty List")
            }
        }
    }
}