package com.george.kmpmoviepoc.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.george.kmpmoviepoc.di.AppModule

sealed class Screen {
    data object Search : Screen()
    data class Detail(val imdbId: String) : Screen()
}

@Composable
fun AppRoot(appModule: AppModule) {
    val searchViewModel = remember { appModule.provideSearchViewModel() }
    val detailViewModel = remember { appModule.provideDetailViewModel() }
    var screen by remember { mutableStateOf<Screen>(Screen.Search) }

    DisposableEffect(Unit) {
        onDispose {
            searchViewModel.clear()
            detailViewModel.clear()
        }
    }

    when (val current = screen) {
        Screen.Search -> SearchScreen(
            viewModel = searchViewModel,
            onMovieSelected = { movie ->
                screen = Screen.Detail(movie.imdbId)
            }
        )
        is Screen.Detail -> DetailScreen(
            viewModel = detailViewModel,
            imdbId = current.imdbId,
            onBack = { screen = Screen.Search }
        )
    }
}
