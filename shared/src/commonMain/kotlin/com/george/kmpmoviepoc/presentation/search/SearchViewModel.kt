package com.george.kmpmoviepoc.presentation.search

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.core.DispatcherProvider
import com.george.kmpmoviepoc.core.asCStateFlow
import com.george.kmpmoviepoc.domain.usecase.SearchMoviesUseCase
import com.george.kmpmoviepoc.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val searchMovies: SearchMoviesUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()
    val stateFlow = _state.asCStateFlow()

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query, errorMessage = null) }
    }

    fun search() {
        val query = state.value.query.trim()
        if (query.isBlank()) {
            _state.update { it.copy(errorMessage = "Type a movie title to search.") }
            return
        }

        scope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            // O(1) local: delegates the heavy work to the use case.
            val result = withContext(ioDispatcher) { searchMovies(query) }
            when (result) {
                is AppResult.Success -> _state.update {
                    it.copy(isLoading = false, movies = result.data, errorMessage = null)
                }
                is AppResult.Error -> _state.update {
                    it.copy(isLoading = false, movies = emptyList(), errorMessage = result.message)
                }
            }
        }
    }
}
