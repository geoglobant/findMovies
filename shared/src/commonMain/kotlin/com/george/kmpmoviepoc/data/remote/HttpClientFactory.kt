package com.george.kmpmoviepoc.data.remote

import io.ktor.client.HttpClient

expect fun provideHttpClient(): HttpClient
