package com.theapache64.topcorn2.ui.screen.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn2.model.Category
import com.theapache64.topcorn2.utils.calladapter.flow.Resource
import com.theapache64.topcorn2.utils.flow.mutableEventFlow
import com.theapache64.topcorn2.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import timber.log.Timber

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

    val sortedOrder = SingleLiveEvent<Int>().apply {
        value = DEFAULT_SORT_ORDER
    }

    val movies = sortedOrder.switchMap { sortOrder ->
        Timber.d("Sort order changed : $sortOrder")
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
            }.asLiveData()
    }

    private val _goToMovieDetail = SingleLiveEvent<Int>()
    val goToMovieDetail: LiveData<Int> = _goToMovieDetail

    fun onMovieClicked(it: Movie) {
        Timber.d("onMovieClicked: Clicked on movie: ${it.id}")
        _goToMovieDetail.value = it.id
    }

    fun onToggleDarkModeClicked(isDarkMode: Boolean) {
        _toggleDarkMode.tryEmit(isDarkMode.not())
    }

    fun onHeartClicked() {

    }

    fun onSortByRatingClicked() {
        sortedOrder.value = SORT_ORDER_RATING
    }

    fun onSortByYearClicked() {
        sortedOrder.value = SORT_ORDER_YEAR
    }

    fun onRetryClicked() {
        // Resetting sort order to fire new data request
        sortedOrder.value = sortedOrder.value
    }

}