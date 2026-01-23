package com.george.kmpmoviepoc

import com.george.kmpmoviepoc.data.mapper.toDomain
import com.george.kmpmoviepoc.data.remote.dto.OmdbMovieDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MovieMapperTest {
    @Test
    fun mapsDtoToDomainAndNullsPosterWhenNA() {
        val dto = OmdbMovieDto(
            title = "Title",
            year = "2024",
            imdbId = "id",
            type = "movie",
            poster = "N/A"
        )

        val movie = dto.toDomain()

        assertEquals("id", movie.imdbId)
        assertNull(movie.posterUrl)
    }
}
