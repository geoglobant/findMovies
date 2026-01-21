import Foundation

enum AppConfig {
    static var omdbApiKey: String {
        let value = Bundle.main.infoDictionary?["OMDB_API_KEY"] as? String
        return value ?? "YOUR_OMDB_API_KEY"
    }
}
