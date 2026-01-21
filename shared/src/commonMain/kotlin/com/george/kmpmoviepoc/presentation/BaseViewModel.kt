package com.george.kmpmoviepoc.presentation

import com.george.kmpmoviepoc.core.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
) {
    private val job = SupervisorJob()
    protected val scope = CoroutineScope(dispatcherProvider.main + job)
    protected val ioDispatcher = dispatcherProvider.io

    fun clear() {
        job.cancel()
        scope.cancel()
    }
}
