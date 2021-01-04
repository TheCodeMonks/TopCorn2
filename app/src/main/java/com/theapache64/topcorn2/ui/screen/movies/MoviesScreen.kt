package com.theapache64.topcorn2.ui.screen.movies

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theapache64.topcorn2.R
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
    val moviesResponseState = moviesViewModel.movies.collectAsState(initial = Resource.Initial())
    val sortOrder by moviesViewModel.sortedOrder.collectAsState()
    val currentUiMode = AmbientConfiguration.current.uiMode

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp,
                title = {
                    Text(
                        text = "TopCorn 2"
                    )
                },
                actions = {
                    AppBarMenu(
                        sortOrder = sortOrder,
                        onSortByStarClicked = {
                            moviesViewModel.onSortByRatingClicked()
                        },
                        onSortByYearClicked = {
                            moviesViewModel.onSortByYearClicked()
                        },
                        onToggleDarkModelClicked = {
                            val isDark = currentUiMode and Configuration.UI_MODE_NIGHT_MASK ==
                                    Configuration.UI_MODE_NIGHT_YES
                            moviesViewModel.onToggleDarkModeClicked(isDark)
                        },
                        onHeartClicked = { /*TODO*/ })
                }
            )
        }
    ) {
        BodyContent(
            moviesResponse = moviesResponseState.value,
            onMovieClicked = {
                moviesViewModel.onMovieClicked(it)
            }
        )
    }
}

@Composable
fun AppBarMenu(
    sortOrder: Int,
    onSortByStarClicked: () -> Unit,
    onSortByYearClicked: () -> Unit,
    onToggleDarkModelClicked: () -> Unit,
    onHeartClicked: () -> Unit
) {
    if (sortOrder == MoviesViewModel.SORT_ORDER_YEAR) {
        // Sort By Star
        IconButton(onClick = { onSortByStarClicked() }) {
            Icon(
                imageVector = vectorResource(id = R.drawable.ic_star)
            )
        }
    } else {
        // Sort By Year
        IconButton(onClick = { onSortByYearClicked() }) {
            Icon(
                imageVector = vectorResource(id = R.drawable.ic_calendar)
            )
        }
    }

    // Dark Mode
    IconButton(
        onClick = {
            onToggleDarkModelClicked()
        }
    ) {
        Icon(
            imageVector = vectorResource(id = R.drawable.ic_switch_dark_mode)
        )
    }

    // Heart
    IconButton(onClick = { onHeartClicked() }) {
        Icon(
            imageVector = vectorResource(id = R.drawable.ic_heart),
            tint = Color.Red
        )
    }
}

@Composable
fun BodyContent(
    moviesResponse: Resource<List<Category>>,
    onMovieClicked: (Movie) -> Unit
) {
    when (moviesResponse) {
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
                itemsIndexed(moviesResponse.data) { _, category ->
                    CategoryRow(
                        category = category,
                        onMovieClicked = onMovieClicked
                    )
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

        // Poster
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Star
            Icon(
                modifier = Modifier.padding(end = 4.dp).preferredSize(12.dp),
                imageVector = vectorResource(id = R.drawable.ic_rating),
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
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