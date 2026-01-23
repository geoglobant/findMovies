package com.george.kmpmoviepoc.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import com.george.kmpmoviepoc.domain.model.Movie
import com.george.kmpmoviepoc.presentation.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onMovieSelected: (Movie) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Find Movies",
            style = MaterialTheme.typography.headlineSmall
        )

        TextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Movie title") },
            colors = TextFieldDefaults.colors()
        )

        Button(
            onClick = viewModel::search,
            enabled = state.query.isNotBlank() && !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!state.isLoading && state.errorMessage == null && state.movies.isEmpty()) {
            Text(
                text = "No results yet. Try searching for a movie.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // O(n): renders each movie card from the list.
            items(state.movies) { movie ->
                MovieRow(movie = movie, onMovieSelected = onMovieSelected)
            }
        }
    }
}

@Composable
private fun MovieRow(
    movie: Movie,
    onMovieSelected: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieSelected(movie) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val posterUrl = movie.posterUrl ?: posterUrl(movie.imdbId, 240)
            AsyncImage(
                model = posterUrl,
                contentDescription = movie.title,
                modifier = Modifier.size(72.dp, 108.dp),
                contentScale = ContentScale.Crop
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${movie.year} • ${movie.type}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
