package com.george.kmpmoviepoc.presentation.detail

import com.george.kmpmoviepoc.domain.model.MovieDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val errorMessage: String? = null
)
