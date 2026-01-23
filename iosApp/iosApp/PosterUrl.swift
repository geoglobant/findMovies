import Foundation

func posterUrl(imdbId: String, height: Int) -> URL? {
    // O(1): builds a URL string.
    let apiKey = AppConfig.omdbPosterApiKey
    if apiKey.isEmpty || apiKey == "YOUR_OMDB_API_KEY" {
        return nil
    }
    let urlString = "https://img.omdbapi.com/?i=\(imdbId)&h=\(height)&apikey=\(apiKey)"
    return URL(string: urlString)
}
