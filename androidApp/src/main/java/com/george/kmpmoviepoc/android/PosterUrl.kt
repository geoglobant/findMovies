package com.george.kmpmoviepoc.android

fun posterUrl(imdbId: String, height: Int): String? {
    // O(1): builds a URL string.
    val apiKey = BuildConfig.OMDB_POSTER_API_KEY
    if (apiKey.isBlank()) return null
    return "https://img.omdbapi.com/?i=$imdbId&h=$height&apikey=$apiKey"
}
