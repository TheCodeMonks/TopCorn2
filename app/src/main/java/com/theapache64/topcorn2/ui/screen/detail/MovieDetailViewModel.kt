package com.theapache64.topcorn2.ui.screen.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.data.repo.MoviesRepo
import com.theapache64.topcorn2.utils.flow.mutableEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.mapLatest

/**
 * Created by theapache64 : Jan 05 Tue,2021 @ 01:09
 */
class MovieDetailViewModel @ViewModelInject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    private val _movieId = MutableStateFlow(-1)

    val movie = _movieId.mapLatest {
        moviesRepo.getMovie(it)
    }

    private val _openImdbUrl = mutableEventFlow<String>()
    val openImdbUrl: SharedFlow<String> = _openImdbUrl

    private val _shouldNavigateUp = mutableEventFlow<Boolean>()
    val shouldNavigateUp: SharedFlow<Boolean> = _shouldNavigateUp

    fun init(movieId: Int) {
        _movieId.tryEmit(movieId)
    }

    fun onOpenImdbClicked(movie: Movie) {
        _openImdbUrl.tryEmit("https://m.imdb.com${movie.imdbPath}")
    }

    fun onBackPressed() {
        _shouldNavigateUp.tryEmit(true)
    }
}