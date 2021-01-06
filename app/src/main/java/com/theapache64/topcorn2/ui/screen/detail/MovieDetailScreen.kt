package com.theapache64.topcorn2.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theapache64.topcorn2.R
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.ui.common.Fakes
import com.theapache64.topcorn2.ui.common.Poster
import com.theapache64.topcorn2.ui.theme.TopCornTheme

/**
 * Created by theapache64 : Jan 05 Tue,2021 @ 01:09
 */
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel
) {
    val movie by viewModel.movie.collectAsState(initial = null)
    movie?.let {
        MovieDetail(
            movie = it
        )
    }
}

@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Column(
        modifier = modifier.padding(
            start = 10.dp,
            top = 10.dp
        )
    ) {

        // Back button
        Icon(
            imageVector = vectorResource(id = R.drawable.ic_back_arrow),
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.clickable(
                onClick = {

                }
            )
        )

        Row(
            modifier = Modifier.padding(top = 12.dp)
        ) {

            // Poster
            Poster(
                modifier = Modifier
                    .preferredWidth(200.dp)
                    .preferredHeight(250.dp)
                    .padding(end = 4.dp),
                movie = movie,
                onMovieClicked = { /*TODO*/ }
            )

            Column {
                // Rating
                MovieMeta(key = "Rating", value = movie.rating.toString())

                // Director
                MovieMeta(key = "Director", value = movie.directors.joinToString(separator = ", "))

                // Starring
                MovieMeta(key = "Starring", value = movie.actors.joinToString(separator = ", "))

                // Genre
                MovieMeta(key = "Genre", value = movie.genre.joinToString(separator = ", "))
            }


        }
    }
}

@Composable
fun MovieMeta(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(modifier = modifier) {
        // Key
        Text(
            text = "$key : ",
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )

        // Value
        Text(text = value)
    }
}

@Preview
@Composable
fun MovieDetailPreview() {
    TopCornTheme {
        MovieDetail(movie = Fakes.getFakeMovie())
    }
}