package com.theapache64.topcorn2.ui.screen.detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
            movie = it,
            onOpenImdbClicked = {
                viewModel.onOpenImdbClicked(it)
            },
            onBackPressed = {
                viewModel.onBackPressed()
            }
        )
    }
}

@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    movie: Movie,
    onOpenImdbClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                modifier = Modifier.requiredHeight(70.dp),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .padding(
                        start = 15.dp,
                        end = 15.dp,
                    )
                    .scrollable(
                        state = scrollState,
                        orientation = Orientation.Vertical
                    )
            ) {

                Row {

                    // Poster
                    Poster(
                        modifier = Modifier
                            .requiredWidth(180.dp)
                            .requiredHeight(250.dp),
                        movie = movie,
                        onMovieClicked = { /*TODO*/ }
                    )

                    // Meta
                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        // Rating
                        MovieMeta(key = "Rating", value = movie.rating.toString())

                        // Director
                        MovieMeta(
                            key = "Director",
                            value = movie.directors.joinToString(separator = ", ")
                        )

                        // Starring
                        MovieMeta(
                            key = "Starring",
                            value = movie.actors.joinToString(separator = ", ")
                        )

                        // Genre
                        MovieMeta(key = "Genre", value = movie.genre.joinToString(separator = ", "))
                    }
                }

                // Title
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 4.dp
                    ),
                    style = MaterialTheme.typography.h5
                )

                // Desc
                Text(
                    text = movie.desc,
                    modifier = Modifier.padding(
                        bottom = 10.dp
                    ),
                    style = MaterialTheme.typography.body1
                )

                // IMDB Button
                OutlinedButton(
                    onClick = {
                        onOpenImdbClicked()
                    }
                ) {
                    Text(text = "OPEN IMDB")
                }
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
    Column(modifier = modifier) {
        // Key
        Text(
            text = key,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )

        // Value
        Text(
            style = MaterialTheme.typography.body1,
            text = value
        )

        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Preview
@Composable
fun MovieDetailPreview() {
    TopCornTheme {
        MovieDetail(
            movie = Fakes.getFakeMovie(),
            onBackPressed = {},
            onOpenImdbClicked = {}
        )
    }
}