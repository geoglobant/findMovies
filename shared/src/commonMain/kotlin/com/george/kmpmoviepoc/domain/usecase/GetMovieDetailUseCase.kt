package com.george.kmpmoviepoc.domain.usecase

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.domain.model.MovieDetail
import com.george.kmpmoviepoc.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(imdbId: String): AppResult<MovieDetail> {
        return repository.getMovieDetail(imdbId)
    }
}
