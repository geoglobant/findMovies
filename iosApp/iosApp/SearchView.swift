import SwiftUI
import shared

final class SearchViewModelWrapper: ObservableObject {
    private let viewModel: SearchViewModel
    private var closeable: Closeable?

    @Published private(set) var state: SearchUiState

    init(appModule: AppModule) {
        viewModel = appModule.provideSearchViewModel()
        state = viewModel.stateFlow.value ?? SearchUiStateKt.defaultSearchUiState()
    }

    func start() {
        guard closeable == nil else { return }
        closeable = viewModel.stateFlow.watch { [weak self] newState in
            self?.state = newState ?? SearchUiStateKt.defaultSearchUiState()
        }
    }

    func stop() {
        closeable?.close()
        closeable = nil
    }

    func onQueryChange(_ query: String) {
        viewModel.onQueryChange(query: query)
    }

    func search() {
        viewModel.search()
    }

    deinit {
        stop()
        viewModel.clear()
    }
}

struct SearchView: View {
    @ObservedObject var viewModel: SearchViewModelWrapper

    private var movieCount: Int {
        viewModel.state.movies.count
    }

    var body: some View {
        VStack(spacing: 12) {
            Text("Find Movies")
                .font(.title2)

            TextField(
                "Movie title",
                text: Binding(
                    get: { viewModel.state.query },
                    set: { viewModel.onQueryChange($0) }
                )
            )
            .textFieldStyle(.roundedBorder)

            Button("Search") {
                viewModel.search()
            }
            .disabled(viewModel.state.query.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty || viewModel.state.isLoading)

            if viewModel.state.isLoading {
                ProgressView()
            }

            if let errorMessage = viewModel.state.errorMessage {
                Text(errorMessage)
                    .foregroundColor(.red)
            }

            if !viewModel.state.isLoading && viewModel.state.errorMessage == nil && movieCount == 0 {
                Text("No results yet. Try searching for a movie.")
                    .foregroundColor(.secondary)
            }

            List {
                ForEach(0..<movieCount, id: \.self) { index in
                    let movie = viewModel.state.movies[index]
                    NavigationLink(value: movie.imdbId) {
                        VStack(alignment: .leading, spacing: 4) {
                            Text(movie.title)
                                .font(.headline)
                            Text("\(movie.year) • \(movie.type)")
                                .foregroundColor(.secondary)
                        }
                    }
                }
            }
        }
        .padding(.horizontal, 16)
    }
}
