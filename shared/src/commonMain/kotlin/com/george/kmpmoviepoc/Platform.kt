package com.george.kmpmoviepoc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform