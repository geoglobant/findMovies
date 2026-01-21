package com.george.kmpmoviepoc.data.remote

import com.george.kmpmoviepoc.data.remote.dto.OmdbDetailResponse
import com.george.kmpmoviepoc.data.remote.dto.OmdbSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://www.omdbapi.com/"

class OmdbApi(
    private val client: HttpClient,
    private val apiKey: String
) {
    suspend fun searchMovies(query: String): OmdbSearchResponse {
        return client.get(BASE_URL) {
            parameter("apikey", apiKey)
            parameter("s", query)
        }.body()
    }

    suspend fun getMovieDetail(imdbId: String): OmdbDetailResponse {
        return client.get(BASE_URL) {
            parameter("apikey", apiKey)
            parameter("i", imdbId)
            parameter("plot", "full")
        }.body()
    }
}
