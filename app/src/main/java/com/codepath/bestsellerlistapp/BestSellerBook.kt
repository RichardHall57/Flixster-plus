package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single movie from the TMDB API.
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the Gson library.
 */
data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("overview") val description: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val rating: Double
)
