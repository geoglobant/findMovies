package com.george.kmpmoviepoc

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.domain.model.MovieDetail
import com.george.kmpmoviepoc.domain.repository.MovieRepository
import com.george.kmpmoviepoc.domain.usecase.GetMovieDetailUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class GetMovieDetailUseCaseTest {
    @Test
    fun returnsRepositoryResult() = runBlocking {
        val fakeRepository = object : MovieRepository {
            override suspend fun searchMovies(query: String) =
                AppResult.Error("Not used in this test")

            override suspend fun getMovieDetail(imdbId: String): AppResult<MovieDetail> {
                return AppResult.Success(
                    MovieDetail(
                        imdbId = imdbId,
                        title = "Title",
                        year = "2024",
                        plot = "Plot",
                        runtime = "120 min",
                        genre = "Action",
                        director = "Director",
                        imdbRating = "7.5",
                        posterUrl = null
                    )
                )
            }
        }

        val useCase = GetMovieDetailUseCase(fakeRepository)
        val result = useCase("id")

        assertTrue(result is AppResult.Success)
        assertEquals("id", (result as AppResult.Success).data.imdbId)
    }
}
