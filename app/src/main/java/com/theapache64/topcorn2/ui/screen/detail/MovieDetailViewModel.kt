package com.theapache64.topcorn2.ui.screen.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.theapache64.topcorn2.data.repositories.movies.MoviesRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

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

    fun init(movieId: Int) {
        _movieId.tryEmit(movieId)
    }

    fun onOpenImdbClicked() {

    }
}