package com.george.kmpmoviepoc.domain.repository

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.domain.model.Movie
import com.george.kmpmoviepoc.domain.model.MovieDetail

interface MovieRepository {
    suspend fun searchMovies(query: String): AppResult<List<Movie>>
    suspend fun getMovieDetail(imdbId: String): AppResult<MovieDetail>
}
