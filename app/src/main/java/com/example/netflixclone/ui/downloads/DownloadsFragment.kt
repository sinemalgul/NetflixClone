package com.example.netflixclone.ui.downloads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.netflixclone.R
import com.example.netflixclone.common.gone
import com.example.netflixclone.common.showSnackbar
import com.example.netflixclone.common.visible
import com.example.netflixclone.databinding.FragmentDownloadsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadsFragment : Fragment() {

    private lateinit var binding: FragmentDownloadsBinding

    private val viewModel by viewModels<DownloadsViewModel>()

    private val downloadsAdapter by lazy {
        DownloadsAdapter(
            onRemoveDownloadsClick = {
                viewModel.deleteDownloads(it)
                requireView().showSnackbar("Deleted")
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDownloads()
        initObservers()

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.downloads_to_home)
        }
    }

    private fun initObservers() = with(binding) {
        viewModel.downloadsState.observe(viewLifecycleOwner) { state ->
            with(state) {
                if (downloadsList.isNullOrEmpty()) {
                    rvDownloads.gone()
                } else {
                    downloadsList.let {
                        rvDownloads.visible()
                        rvDownloads.adapter = downloadsAdapter
                        rvDownloads.setHasFixedSize(true)
                        downloadsAdapter.updateList(it)
                    }
                }
            }
        }
    }

}