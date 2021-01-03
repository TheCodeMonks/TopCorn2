package com.theapache64.topcorn2.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.theapache64.topcorn2.R
import com.theapache64.topcorn2.ui.screen.splash.SplashFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        lifecycleScope.launchWhenStarted {
            mainViewModel.shouldGoToMovies.collect { shouldGoToMovies ->
                if (shouldGoToMovies) {
                    val navController = findNavController(R.id.nav_host)

                    navController.navigate(
                        SplashFragmentDirections.actionSplashFragmentToMoviesFragment()
                    )
                }
            }
        }
    }
}