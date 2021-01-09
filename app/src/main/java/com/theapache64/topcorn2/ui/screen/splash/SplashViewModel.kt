package com.theapache64.topcorn2.ui.screen.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.topcorn2.BuildConfig
import com.theapache64.topcorn2.utils.flow.mutableEventFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 20:57
 */
class SplashViewModel @ViewModelInject constructor() : ViewModel() {

    companion object {
        const val versionNumber: String = "v${BuildConfig.VERSION_NAME}"
        const val SPLASH_DELAY = 1500L
    }

    private val _isSplashTimeFinished = mutableEventFlow<Boolean>()
    val isSplashTimeFinished: SharedFlow<Boolean> = _isSplashTimeFinished

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _isSplashTimeFinished.emit(true)
        }
    }
}