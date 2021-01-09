package com.theapache64.topcorn2.ui.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * An activity to test new APIs.
 * Usually invoked using Android Studio Run Configuration and should not be invoked
 * by any other components
 */
@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observing data
        viewModel.myData.observe(this) {
            Timber.d("Sort order observed : $it")
        }
    }
}