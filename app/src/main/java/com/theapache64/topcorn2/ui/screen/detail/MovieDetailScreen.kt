package com.theapache64.topcorn2.ui.screen.detail

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Created by theapache64 : Jan 05 Tue,2021 @ 01:09
 */
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onClick: () -> Unit
) {
    val movie by viewModel.movie.collectAsState(initial = null)
    if (movie != null) {
        Button(onClick = onClick) {
            Text(text = movie!!.title)
        }
    }
}