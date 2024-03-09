package com.example.netflixclone.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.databinding.ItemSearchBinding
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val onSearchClick: (Int) -> Unit,
) : RecyclerView.Adapter<SearchAdapter.ItemDesign>() {

    private val searchList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ItemDesign {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDesign(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ItemDesign, position: Int) {
        holder.bind(searchList[position])
    }

    inner class ItemDesign(private var binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind( movie: Movie) {
                with(binding) {
                    nameText.text = movie.title

                    movie.poster?.let {
                        Picasso.get().load(it).into(poster)
                    }

                    root.setOnClickListener {
                        onSearchClick(movie.id ?: 0)
                    }
                }
            }
        }

    override fun getItemCount(): Int = searchList.size

    fun updateList(list: List<Movie>) {
        searchList.clear()
        searchList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun clearList() {
        searchList.clear()
        notifyDataSetChanged()
    }
}