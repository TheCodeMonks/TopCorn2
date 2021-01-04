package com.theapache64.topcorn2.ui.screen.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theapache64.topcorn2.data.remote.Movie
import com.theapache64.topcorn2.model.Category
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import com.theapache64.topcorn2.utils.calladapter.flow.Resource
import dev.chrisbanes.accompanist.coil.CoilImage
import timber.log.Timber

/**
 * Created by theapache64 : Jan 04 Mon,2021 @ 00:06
 */
@Composable
fun MoviesScreen(
    moviesViewModel: MoviesViewModel

) {
    val moviesResponse = moviesViewModel.movies.collectAsState(initial = Resource.Initial())
    when (moviesResponse.value) {
        is Resource.Initial -> {
            Timber.d("MoviesScreen: Initial")
        }
        is Resource.Loading -> {
            Timber.d("MoviesScreen: Loading")
            Text(text = "Loading")
        }
        is Resource.Success -> {
            Timber.d("MoviesScreen: Success")
            LazyColumn {
                itemsIndexed((moviesResponse.value as Resource.Success<List<Category>>).data) { index, category ->
                    CategoryRow(category = category) {
                        moviesViewModel.onMovieClicked(it)
                    }
                }
            }
        }
        is Resource.Error -> {
            Timber.d("MoviesScreen: Error")
        }
    }
}


private val cardWidth = 150.dp

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .preferredWidth(cardWidth)
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier.clickable(onClick = { onMovieClicked(movie) })
        ) {
            CoilImage(
                data = movie.thumbUrl,
                fadeIn = true,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredWidth(cardWidth)
                    .preferredHeight(200.dp)
            )
        }
        // Title
        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Rating
        Text(
            text = movie.rating.toString(),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            style = MaterialTheme.typography.overline,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    category: Category,
    onMovieClicked: (Movie) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // Title
        Text(
            text = category.genre,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(
                top = 10.dp,
                start = 10.dp
            )
        )
        LazyRow {
            itemsIndexed(category.movies) { index, movie ->
                MovieItem(movie = movie, onMovieClicked = onMovieClicked)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategory() {
    TopCornTheme {
        CategoryRow(
            category = getFakeCategory()
        ) { movie ->
        }
    }
}

@Composable
private fun getFakeCategory() = Category(
    id = 0,
    genre = "Action",
    movies = listOf(
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
        getFakeMovie(),
    )
)

@Preview
@Composable
fun PreviewMovie() {
    TopCornTheme {
        MovieItem(
            movie = getFakeMovie(),
            onMovieClicked = { /*TODO*/ }
        )
    }
}

@Composable
private fun getFakeMovie() = Movie(
    actors = listOf(),
    desc = "",
    directors = listOf(),
    genre = listOf(),
    imageUrl = "",
    thumbUrl = "https://picsum.photos/id/234/400/600",
    imdbUrl = "",
    title = "Ironman",
    rating = 8f,
    year = 0
)