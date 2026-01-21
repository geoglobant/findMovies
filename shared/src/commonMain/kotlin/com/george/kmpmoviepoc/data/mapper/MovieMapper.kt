package com.george.kmpmoviepoc.data.mapper

import com.george.kmpmoviepoc.data.remote.dto.OmdbDetailResponse
import com.george.kmpmoviepoc.data.remote.dto.OmdbMovieDto
import com.george.kmpmoviepoc.domain.model.Movie
import com.george.kmpmoviepoc.domain.model.MovieDetail

fun OmdbMovieDto.toDomain(): Movie {
    return Movie(
        imdbId = imdbId,
        title = title,
        year = year,
        type = type,
        posterUrl = poster.takeUnless { it.equals("N/A", ignoreCase = true) }
    )
}

fun OmdbDetailResponse.toDomain(): MovieDetail {
    return MovieDetail(
        imdbId = imdbId,
        title = title,
        year = year,
        plot = plot,
        runtime = runtime,
        genre = genre,
        director = director,
        imdbRating = imdbRating,
        posterUrl = poster.takeUnless { it.equals("N/A", ignoreCase = true) }
    )
}
