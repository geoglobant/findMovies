import SwiftUI
import shared

final class DetailViewModelWrapper: ObservableObject {
    private let viewModel: DetailViewModel
    private var closeable: Closeable?

    @Published private(set) var state: DetailUiState

    init(appModule: AppModule) {
        viewModel = appModule.provideDetailViewModel()
        state = viewModel.stateFlow.value ?? DetailUiStateKt.defaultDetailUiState()
    }

    func start() {
        guard closeable == nil else { return }
        closeable = viewModel.stateFlow.watch { [weak self] newState in
            self?.state = newState ?? DetailUiStateKt.defaultDetailUiState()
        }
    }

    func stop() {
        closeable?.close()
        closeable = nil
    }

    func load(imdbId: String) {
        viewModel.load(imdbId: imdbId)
    }

    deinit {
        stop()
        viewModel.clear()
    }
}

struct DetailView: View {
    let imdbId: String
    @StateObject private var viewModel: DetailViewModelWrapper
    @State private var didStart = false

    init(imdbId: String, appModule: AppModule) {
        self.imdbId = imdbId
        _viewModel = StateObject(wrappedValue: DetailViewModelWrapper(appModule: appModule))
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                if viewModel.state.isLoading {
                    ProgressView()
                } else if let errorMessage = viewModel.state.errorMessage {
                    Text(errorMessage)
                        .foregroundColor(.red)
                } else if let movie = viewModel.state.movie {
                    Text(movie.title)
                        .font(.title2)
                    Text("\(movie.year) • \(movie.runtime)")
                        .foregroundColor(.secondary)
                    Text(movie.genre)
                    Text("Director: \(movie.director)")
                    Text("IMDb: \(movie.imdbRating)")
                    Text(movie.plot)
                }
            }
            .padding(16)
        }
        .onAppear {
            if !didStart {
                viewModel.start()
                viewModel.load(imdbId: imdbId)
                didStart = true
            }
        }
    }
}
