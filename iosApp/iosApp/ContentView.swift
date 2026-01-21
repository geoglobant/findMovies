import SwiftUI
import shared

struct ContentView: View {
    private let appModule: AppModule
    @StateObject private var searchViewModel: SearchViewModelWrapper
    @State private var didStart = false

    init() {
        let module = AppModule(apiKey: AppConfig.omdbApiKey)
        self.appModule = module
        _searchViewModel = StateObject(wrappedValue: SearchViewModelWrapper(appModule: module))
    }

    var body: some View {
        NavigationStack {
            SearchView(viewModel: searchViewModel)
                .navigationDestination(for: String.self) { imdbId in
                    DetailView(imdbId: imdbId, appModule: appModule)
                }
                .navigationTitle("Movies")
        }
        .onAppear {
            if !didStart {
                searchViewModel.start()
                didStart = true
            }
        }
    }
}
