package com.george.kmpmoviepoc.data.repository

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.data.mapper.toDomain
import com.george.kmpmoviepoc.data.remote.OmdbApi
import com.george.kmpmoviepoc.data.remote.dto.OmdbDetailResponse
import com.george.kmpmoviepoc.data.remote.dto.OmdbSearchResponse
import com.george.kmpmoviepoc.domain.model.Movie
import com.george.kmpmoviepoc.domain.model.MovieDetail
import com.george.kmpmoviepoc.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: OmdbApi
) : MovieRepository {
    override suspend fun searchMovies(query: String): AppResult<List<Movie>> {
        return try {
            val response = api.searchMovies(query)
            if (!response.isSuccess()) {
                AppResult.Error(response.error ?: "No results found.")
            } else {
                // O(n): map over the result list to build domain models.
                AppResult.Success(response.search.map { it.toDomain() })
            }
        } catch (exception: Exception) {
            AppResult.Error("Unable to reach OMDb.", exception)
        }
    }

    override suspend fun getMovieDetail(imdbId: String): AppResult<MovieDetail> {
        return try {
            val response = api.getMovieDetail(imdbId)
            if (!response.isSuccess()) {
                AppResult.Error(response.error ?: "Movie details not found.")
            } else {
                AppResult.Success(response.toDomain())
            }
        } catch (exception: Exception) {
            AppResult.Error("Unable to reach OMDb.", exception)
        }
    }
}

private fun OmdbSearchResponse.isSuccess(): Boolean =
    response.equals("True", ignoreCase = true)

private fun OmdbDetailResponse.isSuccess(): Boolean =
    response.equals("True", ignoreCase = true)
