package com.george.kmpmoviepoc.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.george.kmpmoviepoc.presentation.detail.DetailViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    imdbId: String,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(imdbId) {
        viewModel.load(imdbId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = onBack) {
            Text(text = "Back")
        }

        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> Text(
                text = state.errorMessage ?: "",
                color = MaterialTheme.colorScheme.error
            )
            state.movie != null -> {
                state.movie?.let { movie ->
                    val poster = movie.posterUrl ?: posterUrl(movie.imdbId, 900)
                    AsyncImage(
                        model = poster,
                        contentDescription = movie.title,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                    Text(text = movie.title, style = MaterialTheme.typography.headlineSmall)
                    Text(
                        text = "${movie.year} • ${movie.runtime}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(text = movie.genre)
                    Text(text = "Director: ${movie.director}")
                    Text(text = "IMDb: ${movie.imdbRating}")
                    Text(text = movie.plot)
                }
            }
        }
    }
}
