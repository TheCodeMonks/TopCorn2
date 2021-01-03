package com.theapache64.topcorn2.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 21:14
 */
class MainViewModel @ViewModelInject constructor() : ViewModel() {
    private val _shouldGoToMovies = MutableStateFlow<Boolean>(false)
    val shouldGoToMovies: StateFlow<Boolean> = _shouldGoToMovies

    fun onSplashTimeFinished() {
        _shouldGoToMovies.value = true
    }
}