package com.george.kmpmoviepoc.domain.usecase

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.domain.model.Movie
import com.george.kmpmoviepoc.domain.repository.MovieRepository

class SearchMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(query: String): AppResult<List<Movie>> {
        return repository.searchMovies(query)
    }
}
