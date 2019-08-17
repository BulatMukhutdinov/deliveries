package tat.mukhutdinov.deliveries.infrastructure.util

import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    Timber.e("Exception in $coroutineContext. $throwable")
}