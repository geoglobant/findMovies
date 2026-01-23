import XCTest
@testable import iosApp
import shared

final class ViewModelWrapperTests: XCTestCase {
    func testSearchViewModelWrapperUpdatesQuery() {
        let module = AppModule(apiKey: "test")
        let wrapper = SearchViewModelWrapper(appModule: module)
        wrapper.start()
        wrapper.onQueryChange("batman")

        let expectation = XCTestExpectation(description: "State updated")
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
            XCTAssertEqual(wrapper.state.query, "batman")
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: 1.0)
        wrapper.stop()
    }

    func testDetailViewModelWrapperShowsErrorForBlankId() {
        let module = AppModule(apiKey: "test")
        let wrapper = DetailViewModelWrapper(appModule: module)
        wrapper.start()
        wrapper.load(imdbId: "")

        let expectation = XCTestExpectation(description: "Error updated")
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
            XCTAssertEqual(wrapper.state.errorMessage, "Missing movie id.")
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: 1.0)
        wrapper.stop()
    }
}
