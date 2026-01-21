package com.george.kmpmoviepoc.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun interface Closeable {
    fun close()
}

class CStateFlow<T>(private val origin: StateFlow<T>) {
    val value: T get() = origin.value

    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)
        scope.launch {
            origin.collect { block(it) }
        }
        return Closeable {
            scope.cancel()
        }
    }
}

fun <T> StateFlow<T>.asCStateFlow(): CStateFlow<T> = CStateFlow(this)
