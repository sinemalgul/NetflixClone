package com.example.netflixclone.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.netflixclone.R
import com.example.netflixclone.common.setViewsGone
import com.example.netflixclone.common.setViewsVisible
import com.example.netflixclone.common.showSnackbar
import com.example.netflixclone.databinding.FragmentHomeBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val moviesAdapter by lazy {
        MoviesAdapter(
            onMoviesClick = {
                val actionMovies = HomeFragmentDirections.homeToMovies(it)
                findNavController().navigate(actionMovies)
            }
        ) }

    private val seriesAdapter by lazy {
        SeriesAdapter(
            onSeriesClick = {
                val actionSeries = HomeFragmentDirections.homeToSeries(it)
                findNavController().navigate(actionSeries)
            }
        ) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies()
        viewModel.getSeries()
        initObserver()

        binding.searchIcon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.homeToSearch())
        }

    }

    private fun initObserver() = with(binding) {
        viewModel.moviesState.observe(viewLifecycleOwner) {state ->

            state.moviesList?.let { moviesList ->
                val randomMovies = moviesList.filter { it.isComingSoon == false }
                rvMoviesList.setHasFixedSize(true)
                rvMoviesList.adapter = moviesAdapter
                moviesAdapter.updateList(randomMovies)
            }

            state.errorMessage?.let {
                requireView().showSnackbar("Empty List")
            }

            state.failMessage?.let {
                requireView().showSnackbar("Empty List")
            }
        }

        viewModel.seriesState.observe(viewLifecycleOwner) {state ->
            state.seriesList?.let { seriesList ->
                rvSeriesList.setHasFixedSize(true)
                rvSeriesList.adapter = seriesAdapter
                seriesAdapter.updateList(seriesList)
            }
            state.errorMessage?.let {
                requireView().showSnackbar("Empty List")
            }

            state.failMessage?.let {
                requireView().showSnackbar("Empty List")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}