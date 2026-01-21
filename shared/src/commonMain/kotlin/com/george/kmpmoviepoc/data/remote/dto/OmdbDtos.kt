package com.george.kmpmoviepoc.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OmdbMovieDto(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("imdbID") val imdbId: String = "",
    @SerialName("Type") val type: String = "",
    @SerialName("Poster") val poster: String = ""
)

@Serializable
data class OmdbSearchResponse(
    @SerialName("Search") val search: List<OmdbMovieDto> = emptyList(),
    @SerialName("Response") val response: String = "False",
    @SerialName("Error") val error: String? = null
)

@Serializable
data class OmdbDetailResponse(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("imdbID") val imdbId: String = "",
    @SerialName("Plot") val plot: String = "",
    @SerialName("Runtime") val runtime: String = "",
    @SerialName("Genre") val genre: String = "",
    @SerialName("Director") val director: String = "",
    @SerialName("imdbRating") val imdbRating: String = "",
    @SerialName("Poster") val poster: String = "",
    @SerialName("Response") val response: String = "False",
    @SerialName("Error") val error: String? = null
)
