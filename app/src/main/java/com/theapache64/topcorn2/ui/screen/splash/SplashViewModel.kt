package com.theapache64.topcorn2.ui.screen.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.theapache64.topcorn2.BuildConfig

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 20:57
 */
class SplashViewModel @ViewModelInject constructor() : ViewModel() {
    companion object {
        const val versionNumber: String = "v${BuildConfig.VERSION_NAME}"
    }
}