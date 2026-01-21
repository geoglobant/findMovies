package com.george.kmpmoviepoc.presentation.search

import com.george.kmpmoviepoc.domain.model.Movie

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null
)
