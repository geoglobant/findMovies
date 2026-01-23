import XCTest

final class AppLaunchTests: XCTestCase {
    func testSearchScreenShowsTitle() {
        let app = XCUIApplication()
        app.launch()
        XCTAssertTrue(app.staticTexts["Find Movies"].waitForExistence(timeout: 2.0))
    }
}
