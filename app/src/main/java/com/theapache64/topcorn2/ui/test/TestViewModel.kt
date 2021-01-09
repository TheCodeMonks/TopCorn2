package com.theapache64.topcorn2.ui.test

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import timber.log.Timber

/**
 * Created by theapache64 : Jan 08 Fri,2021 @ 21:06
 */
class TestViewModel @ViewModelInject constructor() : ViewModel() {

    private val sortOrder = MutableLiveData<String>()

    val myData = sortOrder.map {
        Timber.d("Sort order changed to $it")
        "Sort order is $it"
    }

    init {
        sortOrder.value = "year"
    }

}