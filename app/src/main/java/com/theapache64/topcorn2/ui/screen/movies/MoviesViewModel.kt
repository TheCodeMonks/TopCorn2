package com.theapache64.topcorn2.ui.screen.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn2.model.Category
import com.theapache64.topcorn2.utils.calladapter.flow.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * Created by theapache64 : Jan 04 Mon,2021 @ 00:27
 */
class MoviesViewModel @ViewModelInject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {


    companion object {

        const val SORT_ORDER_YEAR = 1
        private const val SORT_ORDER_RATING = 2

        /**
         * To convert movie list to categorized feed
         */
        fun convertToFeed(movies: List<Movie>, sortOrder: Int): List<Category> {

            val genreSet = mutableSetOf<String>()

            for (movie in movies) {
                for (genre in movie.genre) {
                    genreSet.add(genre)
                }
            }

            val feedItems = mutableListOf<Category>()

            for ((index, genre) in genreSet.withIndex()) {

                val genreMovies = movies
                    .filter { it.genre.contains(genre) }
                    .sortedByDescending {
                        when (sortOrder) {
                            SORT_ORDER_RATING -> it.rating
                            SORT_ORDER_YEAR -> it.year.toFloat()
                            else -> {
                                throw IllegalArgumentException("TSH : sort order '$sortOrder' not managed")
                            }
                        }
                    }

                feedItems.add(
                    Category(
                        index.toLong(),
                        genre,
                        genreMovies
                    )
                )
            }
            return feedItems
        }
    }

    private val sortedOrder = MutableStateFlow(SORT_ORDER_YEAR)

    val movies: Flow<Resource<List<Category>>> = sortedOrder.flatMapLatest { sortOrder ->
        moviesRepo
            .getTop250Movies()
            .map {
                when (it) {
                    is Resource.Initial -> {
                        Resource.Initial()
                    }

                    is Resource.Loading -> {
                        Resource.Loading()
                    }

                    is Resource.Success -> {
                        val movies = it.data
                        val feedItems = convertToFeed(movies, sortOrder)
                        Resource.Success(null, feedItems)
                    }

                    is Resource.Error -> {
                        Resource.Error(it.errorData)
                    }
                }
            }
    }

    fun onMovieClicked(it: Movie) {
        sortedOrder.value = SORT_ORDER_RATING
    }


}