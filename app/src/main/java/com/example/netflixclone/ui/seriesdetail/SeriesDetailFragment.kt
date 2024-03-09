package com.example.netflixclone.ui.seriesdetail

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
import com.example.netflixclone.common.visible
import com.example.netflixclone.databinding.FragmentSeriesBinding
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding

    private val viewModel by viewModels<SeriesDetailViewModel>()

    private val args by navArgs<SeriesDetailFragmentArgs>()

    private lateinit var youTubePlayer: YouTubePlayer

    private var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSeriesDetail(args.idSeries)
        initObservers()

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }

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
                        binding.seasonPicker.visible()
                        binding.episodeDetail.visible()

                    } else {
                        binding.seasonPicker.gone()
                        binding.episodeDetail.gone()
                        binding.detail.visible()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initObservers() = with(binding) {
        viewModel.seriesState.observe(viewLifecycleOwner) { state ->

            state.series?.let {
                tvName.text = it.title
                tvDescription.text = it.description
                tvDate.text = it.date.toString()
                episodeName.text = it.episodeName

                it.episodeImage?.let {
                    Picasso.get().load(it).into(imgEpisode)
                }

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
}