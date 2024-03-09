package com.example.netflixclone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.databinding.ItemPosterBinding
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val onMoviesClick: (Int) -> Unit,
) : RecyclerView.Adapter<MoviesAdapter.ItemDesign>() {

    private val moviesList = ArrayList<Movie>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDesign {
        val binding =
            ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDesign(binding)
    }

    override fun onBindViewHolder(holder: ItemDesign, position: Int) {
        holder.bind(moviesList[position])
    }

    inner class ItemDesign(private var binding: ItemPosterBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(movie: Movie) {
                with(binding) {

                    movie.poster?.let {
                        Picasso.get().load(it).into(poster)
                    }

                    root.setOnClickListener {
                        onMoviesClick(movie.id ?: 0)
                    }
                }
            }
        }

    override fun getItemCount(): Int = moviesList.size

    fun updateList(list: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }


}