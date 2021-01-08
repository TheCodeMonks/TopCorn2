package com.theapache64.topcorn2.ui.test

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

/**
 * Created by theapache64 : Jan 08 Fri,2021 @ 21:06
 */
class TestViewModel @ViewModelInject constructor() : ViewModel() {

    private val sortOrder = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )

    val data = sortOrder.mapLatest {
        Timber.d("Sort order changed to $it")
        "Sort order is $it"
    }

    init {
        sortOrder.tryEmit("year")
    }
}