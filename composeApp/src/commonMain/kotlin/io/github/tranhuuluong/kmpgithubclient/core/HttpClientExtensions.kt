package io.github.tranhuuluong.kmpgithubclient.core

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): DataState<T> {
    return try {
        execute().toDataState()
    } catch (e: Exception) {
        coroutineContext.ensureActive() // rethrow CancellationException
        DataStateError(e)
    }
}

suspend inline fun <reified T> HttpResponse.toDataState(): DataState<T> = when (status.value) {
    in 200..299 -> DataStateSuccess(body<T>())
    else -> DataStateError(Throwable("Something went wrong!"))
}