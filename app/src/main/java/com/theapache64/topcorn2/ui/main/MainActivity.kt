package com.theapache64.topcorn2.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.theapache64.topcorn2.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onBackPressed() {
        findNavController(R.id.nav_host).let { navController ->
            navController.currentDestination.let { currentDestination ->
                if (currentDestination?.id == R.id.movies_fragment) {
                    super.onBackPressed()
                } else {
                    navController.navigateUp()
                }
            }
        }
    }
}