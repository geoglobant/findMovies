package com.george.kmpmoviepoc.presentation.detail

import com.george.kmpmoviepoc.core.AppResult
import com.george.kmpmoviepoc.core.DispatcherProvider
import com.george.kmpmoviepoc.core.asCStateFlow
import com.george.kmpmoviepoc.domain.usecase.GetMovieDetailUseCase
import com.george.kmpmoviepoc.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val getMovieDetail: GetMovieDetailUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()
    val stateFlow = _state.asCStateFlow()

    fun load(imdbId: String) {
        if (imdbId.isBlank()) {
            _state.update { it.copy(errorMessage = "Missing movie id.") }
            return
        }

        scope.launch {
            _state.update { it.copy(isLoading = true, movie = null, errorMessage = null) }
            // O(1) local: delegates the heavy work to the use case.
            val result = withContext(ioDispatcher) { getMovieDetail(imdbId) }
            when (result) {
                is AppResult.Success -> _state.update {
                    it.copy(isLoading = false, movie = result.data, errorMessage = null)
                }
                is AppResult.Error -> _state.update {
                    it.copy(isLoading = false, movie = null, errorMessage = result.message)
                }
            }
        }
    }
}
