package com.example.netflixclone.ui.downloads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.databinding.ItemDownloadsBinding
import com.squareup.picasso.Picasso

class DownloadsAdapter(
    private val onRemoveDownloadsClick: (Int) -> Unit
) :RecyclerView.Adapter<DownloadsAdapter.DownloadsItemDesign>() {

    private val downloadsList = ArrayList<Movie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DownloadsItemDesign {
        val binding =
            ItemDownloadsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DownloadsItemDesign(binding)
    }

    override fun onBindViewHolder(holder: DownloadsItemDesign, position: Int) {
        holder.bind(downloadsList[position])
    }

    inner class DownloadsItemDesign( private var binding: ItemDownloadsBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(movie: Movie) {
                with(binding) {
                    nameText.text = movie.title

                    movie.poster?.let {
                        Picasso.get().load(it).into(poster)
                    }

                    imgDelete.setOnClickListener {
                        onRemoveDownloadsClick(movie.id ?: 1)
                    }
                }
            }
        }

    override fun getItemCount(): Int {
        return downloadsList.size
    }

    fun updateList(list: List<Movie>) {
        downloadsList.clear()
        downloadsList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}