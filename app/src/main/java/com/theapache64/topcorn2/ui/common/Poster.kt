package com.theapache64.topcorn2.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.theapache64.topcorn2.data.remote.Movie
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Poster(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Movie) -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = { onMovieClicked(movie) })
    ) {
        CoilImage(
            data = movie.thumbUrl,
            fadeIn = true,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}