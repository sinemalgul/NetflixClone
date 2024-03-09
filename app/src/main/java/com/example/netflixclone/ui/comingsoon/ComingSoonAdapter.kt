package com.example.netflixclone.ui.comingsoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixclone.R
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.databinding.ItemUpcomingMovieBinding
import com.squareup.picasso.Picasso

class ComingSoonAdapter : RecyclerView.Adapter<ComingSoonAdapter.ItemDesign>() {

    private val moviesList = ArrayList<Movie>()
    private var isClicked = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDesign {
        val binding =
            ItemUpcomingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDesign(binding)
    }

    override fun onBindViewHolder(holder: ItemDesign, position: Int) {
        holder.bind(moviesList[position])
    }


    inner class ItemDesign(private var binding: ItemUpcomingMovieBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(movie: Movie){
                with(binding){
                    titleText.text = movie.title
                    descriptionText.text = movie.description
                    genresText.text = movie.genres

                    movie.imageUrl?.let {
                        Picasso.get().load(it).into(backdropImage)
                    }

                    imgRate.setOnClickListener {
                        if (!isClicked) {
                            imgRate.setImageResource(R.drawable.ic_thumb_up_selected)
                            isClicked = true
                        } else {
                            imgRate.setImageResource(R.drawable.ic_thumb_up)
                            isClicked = false
                        }
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