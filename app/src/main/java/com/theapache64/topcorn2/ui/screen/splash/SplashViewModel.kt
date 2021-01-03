package com.theapache64.topcorn2.ui.screen.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.topcorn2.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 20:57
 */
class SplashViewModel @ViewModelInject constructor() : ViewModel() {
    companion object {
        const val versionNumber: String = "v${BuildConfig.VERSION_NAME}"
        const val SPLASH_DELAY = 1500L
    }

    private val _isSplashTimeFinished = MutableStateFlow(false)
    val isSplashTimeFinished: StateFlow<Boolean> = _isSplashTimeFinished

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _isSplashTimeFinished.value = true
        }
    }
}