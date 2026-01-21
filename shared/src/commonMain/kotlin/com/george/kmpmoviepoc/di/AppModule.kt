package com.george.kmpmoviepoc.di

import com.george.kmpmoviepoc.core.DefaultDispatcherProvider
import com.george.kmpmoviepoc.core.DispatcherProvider
import com.george.kmpmoviepoc.data.remote.OmdbApi
import com.george.kmpmoviepoc.data.remote.provideHttpClient
import com.george.kmpmoviepoc.data.repository.MovieRepositoryImpl
import com.george.kmpmoviepoc.domain.repository.MovieRepository
import com.george.kmpmoviepoc.domain.usecase.GetMovieDetailUseCase
import com.george.kmpmoviepoc.domain.usecase.SearchMoviesUseCase
import com.george.kmpmoviepoc.presentation.detail.DetailViewModel
import com.george.kmpmoviepoc.presentation.search.SearchViewModel

class AppModule(
    apiKey: String,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) {
    private val httpClient = provideHttpClient()
    private val api = OmdbApi(httpClient, apiKey)
    private val repository: MovieRepository = MovieRepositoryImpl(api)
    private val searchMoviesUseCase = SearchMoviesUseCase(repository)
    private val getMovieDetailUseCase = GetMovieDetailUseCase(repository)

    fun provideSearchViewModel(): SearchViewModel {
        return SearchViewModel(searchMoviesUseCase, dispatcherProvider)
    }

    fun provideDetailViewModel(): DetailViewModel {
        return DetailViewModel(getMovieDetailUseCase, dispatcherProvider)
    }
}
