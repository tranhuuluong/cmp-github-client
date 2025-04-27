package io.github.tranhuuluong.kmpgithubclient.core

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

/**
 * Executes an HTTP request safely, converting the response or error into a [DataState].
 *
 * @param execute A function that performs the HTTP request and returns an [HttpResponse].
 * @return A [DataState] representing success with data or an error.
 */
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

/**
 * Converts an [HttpResponse] into a [DataState].
 *
 * - Returns [DataStateSuccess] if the response status is 2xx.
 * - Returns [DataStateError] for other statuses.
 *
 * @return A [DataState] containing the parsed response body or an error.
 */
suspend inline fun <reified T> HttpResponse.toDataState(): DataState<T> = when (status.value) {
    in 200..299 -> DataStateSuccess(body<T>())
    else -> DataStateError(Throwable("Something went wrong!")) // TODO: Distinguish error by statuses
}