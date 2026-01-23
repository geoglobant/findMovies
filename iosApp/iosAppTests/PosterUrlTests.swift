import XCTest
@testable import iosApp

final class PosterUrlTests: XCTestCase {
    override func tearDown() {
        unsetenv("OMDB_POSTER_API_KEY")
        super.tearDown()
    }

    func testPosterUrlReturnsNilWhenKeyMissing() {
        unsetenv("OMDB_POSTER_API_KEY")
        let url = posterUrl(imdbId: "tt3896198", height: 240)
        XCTAssertNil(url)
    }

    func testPosterUrlBuildsUrlWhenKeyPresent() {
        setenv("OMDB_POSTER_API_KEY", "test_key", 1)
        let url = posterUrl(imdbId: "tt3896198", height: 240)
        XCTAssertEqual(
            url?.absoluteString,
            "https://img.omdbapi.com/?i=tt3896198&h=240&apikey=test_key"
        )
    }
}
