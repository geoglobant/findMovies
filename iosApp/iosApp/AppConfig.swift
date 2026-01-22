import Foundation

enum AppConfig {
    private static func envValue(_ key: String) -> String? {
        let value = ProcessInfo.processInfo.environment[key]
        let trimmed = value?.trimmingCharacters(in: .whitespacesAndNewlines)
        return trimmed?.isEmpty == false ? trimmed : nil
    }

    static var omdbApiKey: String {
        let value = envValue("OMDB_API_KEY")
            ?? (Bundle.main.infoDictionary?["OMDB_API_KEY"] as? String)
        return value ?? "YOUR_OMDB_API_KEY"
    }

    static var omdbPosterApiKey: String {
        let value = envValue("OMDB_POSTER_API_KEY")
            ?? (Bundle.main.infoDictionary?["OMDB_POSTER_API_KEY"] as? String)
        return value ?? "YOUR_OMDB_API_KEY"
    }
}
