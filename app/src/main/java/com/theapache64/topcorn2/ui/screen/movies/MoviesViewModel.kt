package com.theapache64.topcorn2.ui.screen.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.data.repo.MoviesRepo
import com.theapache64.topcorn2.model.Category
import com.theapache64.topcorn2.model.MoviesRequest
import com.theapache64.topcorn2.utils.calladapter.flow.Resource
import com.theapache64.topcorn2.utils.flow.mutableEventFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

/**
 * Created by theapache64 : Jan 04 Mon,2021 @ 00:27
 */
class MoviesViewModel @ViewModelInject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {


    companion object {

        private const val GITHUB_URL = "https://github.com/TheCodeMonks/topcorn2"

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

    private val _sortOrderToast = mutableEventFlow<Int>()
    val sortOrderToast: SharedFlow<Int> = _sortOrderToast

    private val _openGitHubUrl = mutableEventFlow<String>()
    val openGitHubUrl: SharedFlow<String> = _openGitHubUrl

    val moviesRequest = MutableLiveData(MoviesRequest(SORT_ORDER_YEAR))

    // When ever sortOrder changed, load movies
    val moviesResponse = moviesRequest.switchMap { moviesRequest ->
        Timber.d("Sort order changed")
        liveData<Resource<List<Category>>> {
            moviesRepo
                .getTop250Movies()
                .onStart {
                    emit(Resource.Loading())
                }
                .catch {
                    emit(Resource.Error(it.message ?: "Something went wrong"))
                }
                .collect { movies ->
                    Timber.d("Resp is: ${movies.size} size")
                    _sortOrderToast.emit(moviesRequest.sortOrder)

                    val feedItems = convertToFeed(movies, moviesRequest.sortOrder)

                    emit(Resource.Success(null, feedItems))
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
        _openGitHubUrl.tryEmit(GITHUB_URL)
    }

    fun onSortByRatingClicked() {
        moviesRequest.value = MoviesRequest(SORT_ORDER_RATING)
    }

    fun onSortByYearClicked() {
        moviesRequest.value = MoviesRequest(SORT_ORDER_YEAR)
    }

    fun onRetryClicked() {
        Timber.d("onRetryClicked: Retry clicked")
        // Resetting sort order to fire new data request
        moviesRequest.value = moviesRequest.value!!.copy()
    }

}