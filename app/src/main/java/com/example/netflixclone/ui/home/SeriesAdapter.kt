package com.example.netflixclone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixclone.data.model.Series
import com.example.netflixclone.databinding.ItemPosterBinding
import com.squareup.picasso.Picasso

class SeriesAdapter(
    private var onSeriesClick : (Int) -> Unit,
): RecyclerView.Adapter<SeriesAdapter.ItemDesign>() {

    private val seriesList = ArrayList<Series>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDesign {
        val binding =
            ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDesign(binding)
    }

    override fun onBindViewHolder(holder: ItemDesign, position: Int) {
        holder.bind(seriesList[position])
    }

    inner class ItemDesign(private var binding: ItemPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(series: Series) {
                with(binding) {

                    series.poster?.let {
                        Picasso.get().load(it).into(poster)
                    }

                    root.setOnClickListener {
                        onSeriesClick(series.id ?: 0)
                    }
                }
            }
        }

    override fun getItemCount(): Int = seriesList.size

    fun updateList(list: List<Series>) {
        seriesList.clear()
        seriesList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}