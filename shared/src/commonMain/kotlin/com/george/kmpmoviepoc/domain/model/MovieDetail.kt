package com.george.kmpmoviepoc.domain.model

data class MovieDetail(
    val imdbId: String,
    val title: String,
    val year: String,
    val plot: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val imdbRating: String,
    val posterUrl: String?
)
