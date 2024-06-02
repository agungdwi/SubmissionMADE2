package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemMovieBinding
import com.example.core.domain.model.Movie


class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClick: ((Movie) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {

            binding.apply {
                tvTitle.text = data.title
                val imagePath =
                    if (data.posterPath != "") "https://image.tmdb.org/t/p/original/${data.posterPath}" else R.drawable.image_not_found

                Glide.with(itemView.context)
                    .load(imagePath)
                    .placeholder(R.drawable.placeholder)
                    .into(ivMovie)

                ivMovie.contentDescription = data.title

            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }

        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}