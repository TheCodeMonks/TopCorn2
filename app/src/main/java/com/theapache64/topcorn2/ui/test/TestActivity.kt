package com.theapache64.topcorn2.ui.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.asLiveData
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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

        viewModel.data.asLiveData().observe(this) {
            Timber.d("onCreate: New data is $it")
        }

        /*setContent {
            TopCornTheme {
                val data by viewModel.data.collectAsState(initial = "SOME_DEFAULT_DATA")
                Timber.d("onCreate: Data is $data")
                Surface {
                    Text(text = data)
                }
            }
        }*/
    }
}