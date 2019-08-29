package tat.mukhutdinov.deliveries.infrastructure.util

import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Timber.e(throwable)
}