package com.theapache64.topcorn2.ui.screen.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn2.model.Category
import com.theapache64.topcorn2.utils.calladapter.flow.Resource
import com.theapache64.topcorn2.utils.flow.mutableEventFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map

/**
 * Created by theapache64 : Jan 04 Mon,2021 @ 00:27
 */
class MoviesViewModel @ViewModelInject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {


    companion object {

        const val SORT_ORDER_YEAR = 1
        const val SORT_ORDER_RATING = 2
        const val DEFAULT_SORT_ORDER = SORT_ORDER_YEAR

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


    private val _toggleDarkMode = mutableEventFlow<Boolean>()
    val toggleDarkMode: SharedFlow<Boolean> = _toggleDarkMode

    /**
     * The usage of SingleLiveEvent will be replace when we've an answer for
     * https://stackoverflow.com/questions/65633219/sharedflow-maplatest-not-getting-triggered
     */
    var sortOrder by mutableStateOf(SORT_ORDER_RATING)

    private val _sortOrderToast = mutableEventFlow<Int>()
    val sortOrderToast: SharedFlow<Int> = _sortOrderToast

    // When ever sortOrder changed, load movies
    val movies: Flow<Resource<List<Category>>>
        get() {
            return moviesRepo
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

    private val _goToMovieDetail = mutableEventFlow<Int>()
    val goToMovieDetail: SharedFlow<Int> = _goToMovieDetail

    fun onMovieClicked(it: Movie) {
        _goToMovieDetail.tryEmit(it.id)
    }

    fun onToggleDarkModeClicked(isDarkMode: Boolean) {
        _toggleDarkMode.tryEmit(isDarkMode.not())
    }

    fun onHeartClicked() {

    }

    fun onSortByRatingClicked() {
        sortOrder = SORT_ORDER_RATING
    }

    fun onSortByYearClicked() {
        sortOrder = SORT_ORDER_YEAR
    }

    fun onRetryClicked() {
        // Resetting sort order to fire new data request
        sortOrder = sortOrder
    }

}