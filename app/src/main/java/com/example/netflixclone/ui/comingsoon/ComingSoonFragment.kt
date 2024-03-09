package com.example.netflixclone.ui.comingsoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.netflixclone.R
import com.example.netflixclone.common.showSnackbar
import com.example.netflixclone.databinding.FragmentComingSoonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : Fragment() {

    private var _binding: FragmentComingSoonBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ComingSoonViewModel>()

    private val adapter by lazy { ComingSoonAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComingSoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getComingSoonMovies()
        initObserver()

    }

    private fun initObserver() = with(binding) {
        viewModel.moviesState.observe(viewLifecycleOwner) {state ->

            state.moviesList?.let { moviesList ->
                rvMovies.setHasFixedSize(true)
                rvMovies.adapter = adapter
                adapter.updateList(moviesList)
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