package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.flixster.OnListFragmentInteractionListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/**
 * The class for the main fragment in the Flixster+ app.
 * Fetches and displays movies from TMDB API.
 */
class MoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false)
        val progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progress)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        GridLayoutManager(view.context, 2).also { recyclerView.layoutManager = it }
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                progressBar.hide()

                try {
                    val resultsJSON = json.jsonObject.getJSONArray("results")
                    val moviesRawJSON = resultsJSON.toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                    val movies: List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)

                    recyclerView.adapter = MoviesRecyclerViewAdapter(movies, this@MoviesFragment)
                    Log.d("MoviesFragment", "Movies loaded successfully")
                } catch (e: Exception) {
                    Log.e("MoviesFragment", "Failed to parse movies JSON", e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressBar.hide()
                Log.e("MoviesFragment", "API call failed: $errorResponse")
            }
        })
    }

    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "Clicked: ${item.title}", Toast.LENGTH_LONG).show()
    }
}

