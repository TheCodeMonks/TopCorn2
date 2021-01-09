package com.theapache64.topcorn2.ui.test

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by theapache64 : Jan 08 Fri,2021 @ 21:06
 */
class TestViewModel @ViewModelInject constructor() : ViewModel() {

    private val sortOrder = MutableStateFlow("year")

    val myData = sortOrder.map {
        Timber.d("Sort order changed to $it")
        "Sort order is $it"
    }.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        1
    )

    init {
        viewModelScope.launch {
            delay(4000)
            sortOrder.value = "rating"
        }
    }

}

