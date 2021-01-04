package com.theapache64.topcorn2.model

import com.theapache64.topcorn2.data.remote.Movie


data class Category(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
)
