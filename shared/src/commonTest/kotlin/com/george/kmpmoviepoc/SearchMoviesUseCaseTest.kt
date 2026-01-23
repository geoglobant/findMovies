package com.george.kmpmoviepoc

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.domain.model.Movie
import com.george.kmpmoviepoc.domain.repository.MovieRepository
import com.george.kmpmoviepoc.domain.usecase.SearchMoviesUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class SearchMoviesUseCaseTest {
    @Test
    fun returnsRepositoryResult() = runBlocking {
        val fakeRepository = object : MovieRepository {
            override suspend fun searchMovies(query: String): AppResult<List<Movie>> {
                return AppResult.Success(
                    listOf(Movie("id", "Title", "2024", "movie", null))
                )
            }

            override suspend fun getMovieDetail(imdbId: String) =
                AppResult.Error("Not used in this test")
        }

        val useCase = SearchMoviesUseCase(fakeRepository)
        val result = useCase("batman")

        assertTrue(result is AppResult.Success)
        assertEquals(1, (result as AppResult.Success).data.size)
    }
}
