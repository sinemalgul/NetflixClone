package com.example.netflixclone.ui.moviesdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.netflixclone.R
import com.example.netflixclone.common.gone
import com.example.netflixclone.common.setViewsVisible
import com.example.netflixclone.common.showSnackbar
import com.example.netflixclone.common.visible
import com.example.netflixclone.databinding.FragmentMoviesBinding
import com.example.netflixclone.ui.home.HomeFragmentDirections
import com.example.netflixclone.ui.home.HomeViewModel
import com.example.netflixclone.ui.home.MoviesAdapter
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    private val viewModel by viewModels<MoviesDetailViewModel>()

    private val homeViewModel by viewModels<HomeViewModel>()

    private val args by navArgs<MoviesDetailFragmentArgs>()

    private lateinit var youTubePlayer: YouTubePlayer

    private var isClicked = false

    private val moviesAdapter by lazy {
        MoviesAdapter(
            onMoviesClick = {
                val actionMovies = MoviesDetailFragmentDirections.moviesToMovies(it)
                findNavController().navigate(actionMovies)
            }
        ) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMoviesDetail(args.idMovies)
        homeViewModel.getMovies()
        initObservers()
        initMoviesObserver()
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }

        binding.btnDownload.setOnClickListener{
            viewModel.setDownloadState()
            requireView().showSnackbar("Downloaded")
            findNavController().navigate(MoviesDetailFragmentDirections.moviesToDownload())
        }

        binding.imgRate.setOnClickListener {
            if (!isClicked) {
                binding.imgRate.setImageResource(R.drawable.ic_thumb_up_selected)
                isClicked = true
            } else {
                binding.imgRate.setImageResource(R.drawable.ic_thumb_up)
                isClicked = false
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it.position == 0) {
                        binding.detail.gone()
                        binding.rvMoviesList.visible()
                        initMoviesObserver()
                    } else {
                        binding.rvMoviesList.gone()
                        binding.detail.visible()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initObservers() = with(binding) {
        viewModel.movieState.observe(viewLifecycleOwner) { state ->

            state.movie?.let {
                tvName.text = it.title
                tvDescription.text = it.description
                tvDate.text = it.date.toString()
                tvGenres.text = it.genres

                youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        super.onReady(player)
                        youTubePlayer = player
                        val trailerId = it.trailer
                        player.cueVideo(trailerId, 0f)
                    }
                })
            }
        }
    }

    private fun initMoviesObserver() = with(binding) {
        homeViewModel.moviesState.observe(viewLifecycleOwner) { state ->

            state.moviesList?.let {
                val randomMovies = it.shuffled().take(6)
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
    }
}