package com.codepath.bestsellerlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.flixster.OnListFragmentInteractionListener


//


/**
 * [RecyclerView.Adapter] that can display a [Movie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MoviesRecyclerViewAdapter(
    private val movies: List<Movie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.fragment_best_seller_book,
                parent,
                false
            )  // Ensure this layout exists
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMovieTitle: TextView = mView.findViewById(R.id.movie_title)
        val mMovieOverview: TextView = mView.findViewById(R.id.movie_overview)
        val mMovieRating: TextView = mView.findViewById(R.id.movie_rating)
        val moviePoster: ImageView = mView.findViewById(R.id.movie_poster)

        override fun toString(): String {
            return mMovieTitle.text.toString() + " - " + mMovieOverview.text
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Setting movie details
        holder.mMovieTitle.text = movie.title
        holder.mMovieOverview.text = movie.description
        holder.mMovieRating.text = "⭐ ${movie.rating ?: "N/A"}/10"

        // Loading the movie poster with Glide
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}") // Full URL for the image
            .into(holder.moviePoster)

        // Handling item click
        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int = movies.size

}

